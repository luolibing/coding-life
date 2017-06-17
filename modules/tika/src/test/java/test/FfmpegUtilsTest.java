package test;

import cn.tim.tika.utils.FfmpegUtils;
import com.beust.jcommander.internal.Maps;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Created by LuoLiBing on 15/7/18.
 */
public class FfmpegUtilsTest {


    @Test
    public void checkTest() {
        assert FfmpegUtils.check();
    }

    @Test
    public void transferFileToTargetPath() throws TikaException, IOException, SAXException {
        String classPath = this.getClass().getClassLoader().getResource("").getPath();
        FfmpegUtils.transferFileToTargetPath(classPath + "5虎克显微镜invent.m4v", classPath + "5虎克显微镜invent_output.mp4");
        assert Files.exists(Paths.get(classPath + "5虎克显微镜invent_output.mp4"));
    }


    @Test
    public void transferFileToTargetType() throws TikaException, IOException, SAXException {
        String classPath = this.getClass().getClassLoader().getResource("").getPath();
        FfmpegUtils.transferFileToTargetType(classPath + "5虎克显微镜invent.m4v", "mp4");
        assert Files.exists(Paths.get(classPath + "5虎克显微镜invent.mp4"));
    }

    @Test
    public void getFileMetadata() throws TikaException, IOException, SAXException {
        String classPath = this.getClass().getClassLoader().getResource("").getPath();
        Metadata fileMetadata = FfmpegUtils.getFileMetadata(classPath + "5虎克显微镜invent.m4v");

        Map<String, String> metaMap = Maps.newHashMap();
        for (String tikaKey : fileMetadata.names())
        {
            metaMap.put(tikaKey, metaMap.get(tikaKey));
        }
        System.out.println(metaMap);
    }

    @Test
    public void scanVideoFiles() throws TikaException, IOException, SAXException {
        String basePath = "/share/资源/视频";
        List<String> strings = FfmpegUtils.scanEnableVideo(Paths.get(basePath));
        for(String str:strings)
            System.out.println(str);
    }

    @Test
    public void batchTransferFiles() throws TikaException, IOException, SAXException {
        String basePath = "/share/资源/视频";
        FfmpegUtils.batchParse(basePath, "/share/资源/视频-bak/", "mp4");
    }

}
