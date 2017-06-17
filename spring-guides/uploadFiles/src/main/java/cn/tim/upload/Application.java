package cn.tim.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by LuoLiBing on 15/10/8.
 */
@SpringBootApplication
@Controller
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @RequestMapping(value = "/upload/single", method = RequestMethod.POST)
    public @ResponseBody String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if(!file.isEmpty()) {
            /*byte[] bytes = file.getBytes();
            BufferedOutputStream bufferedOutputStream =
                    new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.close();*/
            // 转存
            file.transferTo(new File(file.getOriginalFilename()));
        }
        return "success";
    }


    @RequestMapping(value = "/upload/multipart", method = RequestMethod.POST)
    public @ResponseBody String uploadFiles(@RequestParam("files") MultipartFile[] files) throws IOException {
        for(MultipartFile file: files) {
            file.transferTo(new File("/share/" + file.getOriginalFilename()));
        }
        return "success";
    }
}
