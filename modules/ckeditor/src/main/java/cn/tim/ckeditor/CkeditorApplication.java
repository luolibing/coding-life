package cn.tim.ckeditor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.UUID;

/**
 * User: luolibing
 * Date: 2018/1/18 10:08
 */
@Controller
@SpringBootApplication
public class CkeditorApplication implements ServletContextAware {

    @Value("${server.port}")
    private String serverPort;
    private String baseUrl;

    public static void main(String[] args) {
        SpringApplication.run(CkeditorApplication.class, args);
    }

    private ServletContext servletContext;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/froala")
    public String froala() {
        return "index-froala";
    }

    @GetMapping("/quill")
    public String quill() {
        return "index-quill";
    }

    @PostMapping("/uploadImage")
    @ResponseBody
    public Object uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String picName = UUID.randomUUID().toString() + ".jpg";
        String realPath = servletContext.getRealPath("/images");
        if(Files.notExists(Paths.get(realPath))) {
            Paths.get(realPath).toFile().mkdirs();
        }
        String targetPath = realPath + File.separator + picName;
        File target = new File(targetPath);
        file.transferTo(target);
        return Collections.singletonMap("link", baseUrl + "images/" + picName);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
        baseUrl = "//127.0.0.1:" + serverPort + "/" + servletContext.getContextPath();
    }
}
