package cn.tim.multipart.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by LuoLiBing on 16/4/18.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "forward: index.html";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void fileUpload(@RequestParam("files") MultipartFile[] files) {
        for(MultipartFile file: files) {
            System.out.println(file.getOriginalFilename());
        }
    }
}
