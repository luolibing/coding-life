package cn.tim.tika;

import cn.tim.tika.utils.FfmpegUtils;
import com.google.common.collect.Maps;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by LuoLiBing on 15/7/18.
 */
@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @RequestMapping(value = "/getFileMetadata", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> getFileMetadata(String filePath) {
        Map<String, Object> result = Maps.newHashMap();
        try {
            Metadata fileMetadata = FfmpegUtils.getFileMetadata(filePath);
            Map<String, String> metaMap = Maps.newHashMap();
            for (String tikaKey : fileMetadata.names()) {
                metaMap.put(tikaKey, fileMetadata.get(tikaKey));
            }
            result.put("success", "Y");
            result.put("metadata", metaMap);
            result.put("type", FfmpegUtils.analysisType(fileMetadata));
        } catch (IOException|TikaException|SAXException e) {
            result.put("success", "N");
            result.put("message", e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/checkEnableVideo", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> checkEnableVideo(String filePath) {
        Map<String, Object> result = Maps.newHashMap();
        try {
            boolean enable = FfmpegUtils.checkEnableVideo(filePath);
            result.put("success", "Y");
            result.put("enable", enable);
        } catch (IOException|TikaException|SAXException e) {
            result.put("success", "N");
            result.put("message", e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/transferFileToTargetPath", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> transferFileToTargetPath(String srcPath, String targetPath) {
        Map<String, Object> result = Maps.newHashMap();
        try {
            Metadata metadata = FfmpegUtils.transferFileToTargetPath(srcPath, targetPath);
            result.put("success", "Y");
            result.put("path", targetPath);
            Map<String, String> metaMap = Maps.newHashMap();
            for (String tikaKey : metadata.names()) {
                metaMap.put(tikaKey, metadata.get(tikaKey));
            }
            result.put("metadata", metaMap);
        } catch (IOException|TikaException|SAXException e) {
            result.put("success", "N");
            result.put("message", e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/transferFileToTargetType", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> transferFileToTargetType(String srcPath, String type) {
        Map<String, Object> result = Maps.newHashMap();
        try {
            String targetPath = srcPath.substring(0, srcPath.lastIndexOf(".") + 1) + type;
            Metadata metadata = FfmpegUtils.transferFileToTargetPath(srcPath, targetPath);
            result.put("success", "Y");
            result.put("path", targetPath);
            Map<String, String> metaMap = Maps.newHashMap();
            for (String tikaKey : metadata.names()) {
                metaMap.put(tikaKey, metadata.get(tikaKey));
            }
            result.put("metadata", metaMap);
        } catch (IOException|TikaException|SAXException e) {
            result.put("success", "N");
            result.put("message", e.getMessage());
        }
        return result;
    }

}
