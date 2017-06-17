package cn.tim.bebase3.helper.web;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

;

/**
 * Created by LuoLiBing on 16/12/16.
 */
public class DownLoads {

    private static RestTemplate restTemplate = new RestTemplate();

    public static void download(String uri, String path) throws IOException {
        byte[] data = restTemplate.getForObject(uri, byte[].class);
        Files.write(Paths.get(path, System.currentTimeMillis() + ".jpg"), data);
    }

    public static void main(String[] args) throws IOException {
        DownLoads.download("http://avatars.tim.cn/user/570038/figure/1447811294.jpg", "/Users/tim/Downloads/avatars");
    }
}
