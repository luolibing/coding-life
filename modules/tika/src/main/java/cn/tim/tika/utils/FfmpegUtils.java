package cn.tim.tika.utils;

import cn.tim.tika.ffmepeg.TikaIntrinsicAVFfmpegParserFactory;
import cn.tim.tika.metadata.PBCore;
import cn.tim.tika.parser.FfmpegExternalParser;
import com.google.common.collect.Lists;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.ToTextContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuoLiBing on 15/7/18.
 */
public class FfmpegUtils {

    private final static Logger log = LoggerFactory.getLogger(FfmpegUtils.class);

    private final static String FFMPEG_COMMAND = "ffmpeg";

    public static boolean check() {
        return FfmpegExternalParser.check(FFMPEG_COMMAND + " -version", 136, 137);
    }


    public static Metadata transferFileToTargetPath(String srcPath, String targetPath) throws IOException, TikaException, SAXException {
        Parser parser = getTransferParser(srcPath, targetPath);
        Metadata metadata = new Metadata();
        try {
            parser.parse(
                    Files.newInputStream(Paths.get(srcPath)),
                    new ToTextContentHandler(),
                    metadata,
                    new ParseContext());
        } finally {
            parser = null;
        }
        return metadata;
    }


    public static Metadata transferFileToTargetType(String srcPath, String extName) throws TikaException, IOException, SAXException {
        String targetPath = srcPath.substring(0, srcPath.lastIndexOf(".") + 1) + extName;
        return transferFileToTargetPath(srcPath, targetPath);
    }


    public static Metadata getFileMetadata(String srcPath) throws IOException, TikaException, SAXException {

        Metadata result = new Metadata();
        Parser parser = getMetadataParser(srcPath);
        try {
            parser.parse(
                    Files.newInputStream(Paths.get(srcPath)),
                    new ToTextContentHandler(),
                    result,
                    new ParseContext());
            log.info("transfer file " + srcPath);
            return result;
        } finally {
            parser = null;
        }
    }


    public static List<String> scanEnableVideo(Path basePath) throws TikaException, IOException, SAXException {
        List<String> enableList = Lists.newArrayList();
        return scanNestEnableVideo(basePath, enableList);
    }


    private static List<String> scanNestEnableVideo(Path basePath, List<String> enableList) throws TikaException, IOException, SAXException {
        File[] files = basePath.toFile().listFiles();
        for(File f: files) {
            if(f.isDirectory()) {
                System.out.println("检查文件夹:" + f.getPath());
                List<String> strings = scanNestEnableVideo(f.toPath(), enableList);
                if(strings!=null)
                    enableList.addAll(strings);
            } else {
                System.out.println("检查文件:" + f.getPath());
                if (checkEnableVideo(f.getPath()))
                    System.out.println("视频:" + f.getPath());
                    //enableList.add(f.getPath());
            }
        }
        return enableList;
    }


    public static boolean checkEnableVideo(String srcPath) throws TikaException, IOException, SAXException {
        Metadata fileMetadata = getFileMetadata(srcPath);
        return checkEnableVideo(fileMetadata);
    }

    public static boolean checkEnableVideo(Metadata metadata) {
        return "h264".equalsIgnoreCase(metadata.get(PBCore.ESSENCE_TRACK_ENCODING(0)));
    }


    public static boolean checkVideo(String srcPath) throws TikaException, IOException, SAXException {
        Metadata fileMetadata = getFileMetadata(srcPath);
        return checkVideo(fileMetadata);
    }

    /**
     * 视频是video和audio的组合
     * @param metadata
     * @return
     */
    public static boolean checkVideo(Metadata metadata) {
        return "VIDEO".equalsIgnoreCase(metadata.get(PBCore.ESSENCE_TRACK_TYPE(0)))
                && "AUDIO".equalsIgnoreCase(metadata.get(PBCore.ESSENCE_TRACK_TYPE(1))) ;
    }

    public static boolean checkImage(String srcPath) throws TikaException, IOException, SAXException {
        Metadata fileMetadata = getFileMetadata(srcPath);
        return checkImage(fileMetadata);
    }

    public static boolean checkImage(Metadata metadata) {
        return "mjpeg".equalsIgnoreCase(metadata.get(PBCore.ESSENCE_TRACK_ENCODING(0)));
    }

    public static boolean checkAudio(Metadata metadata) {
        return "audio".equalsIgnoreCase(metadata.get(PBCore.ESSENCE_TRACK_TYPE(0)));
    }

    public static Map<String, String> analysisType(Metadata metadata) {
        Map typeMap = new HashMap();
        if(checkVideo(metadata)) {
            typeMap.put("type", "video");
            typeMap.put("enable", checkEnableVideo(metadata));
        } else if(checkImage(metadata)) {
            typeMap.put("type", "image");
        } else if(checkAudio(metadata)) {
            typeMap.put("type", "audio");
        }
        return typeMap;
    }


    public static void batchParse(String basePath, String targetBasePath, String type) throws TikaException, IOException, SAXException {
        File file = Paths.get(basePath).toFile();
        File[] files = file.listFiles();
        for(File f:files) {
            String path = f.getPath();
            transferFileToTargetPath(path, targetBasePath + f.getName().substring(0, f.getName().lastIndexOf(".")+1) + type);
        }
    }


    private static Parser getTransferParser(String srcPath, String targetPath) {
        //String command = String.format("%s -i %s -y %s", FFMPEG_COMMAND, srcPath, targetPath);
        String[] command = new String[]{ FFMPEG_COMMAND, "-i", srcPath, "-y", targetPath};
        FfmpegExternalParser parser = TikaIntrinsicAVFfmpegParserFactory.createDefaultInstance(command);
        return parser;
    }


    private static Parser getMetadataParser(String srcPath) {
        String[] command = new String[]{ FFMPEG_COMMAND, "-i", srcPath};
        FfmpegExternalParser parser = TikaIntrinsicAVFfmpegParserFactory.createDefaultInstance(command);
        return parser;
    }

}
