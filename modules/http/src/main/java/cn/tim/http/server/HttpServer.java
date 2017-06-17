package cn.tim.http.server;

import cn.tim.http.utils.HttpRequestSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by LuoLiBing on 16/7/28.
 */
@RequestMapping(value = "/server")
@RestController
public class HttpServer {

    @RequestMapping(value = "get/{id}", method = { RequestMethod.GET, RequestMethod.HEAD})
    public Person get(@PathVariable Long id, HttpServletRequest request) {
        HttpRequestSupport.parseRequest(request);
        return PersonProvider.findOne(id).orElse(Person.nullPerson());
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Person post(Person person) {
        return PersonProvider.addPerson(person);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable Long id) {
        return PersonProvider.deleteOne(id);
    }

    @RequestMapping(value = "trace", method = RequestMethod.TRACE)
    public Object trace() {
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "options", method = {RequestMethod.OPTIONS})
    public Object options() {
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "put", method = RequestMethod.PUT)
    public Object put(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        return ResponseEntity.ok().build();
    }

    /**
     * http协议是无状态协议,不会对之前发生过的请求和响应的状态进行管理
     * @return
     */
    @RequestMapping(value = "cookie", method = RequestMethod.GET)
    public Object cookie(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getRequestURI());
        HttpRequestSupport.parseRequest(request);
        response.addCookie(new Cookie("name", "luolibing"));
        return ResponseEntity.ok().build();
    }

    /**
     * 发送多种数据的多部分对象集合
     * 在请求头里面有一个
     * content-type: multipart/form-data;  boundary=---WebKitFormBoundaryB7DyXAWCaXiy1cJZ
     * --WebKitFormBoundaryB7DyXAWCaXiy1cJZ
     * contentfdsfd5435342432
     * --WebKitFormBoundaryB7DyXAWCaXiy1cJZ
     * content-type: multipart/byteranges;
     * mime多用途因特网邮件扩展机制
     * @return
     */
    @RequestMapping(value = "multipart", method = RequestMethod.POST)
    public Object multipart(HttpServletRequest request, HttpServletResponse response) {
        HttpRequestSupport.parseRequest(request);
        return ResponseEntity.ok().build();
    }
}
