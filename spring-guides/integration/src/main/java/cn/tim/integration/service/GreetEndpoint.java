package cn.tim.integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by LuoLiBing on 16/1/21.
 */
@MessageEndpoint
public class GreetEndpoint {

    @Autowired
    private HelloWorldService helloWorldService;

    /**
     * 催化方法,文件输入,内容输出
     * @param input
     * @return
     * @throws Exception
     */
    @ServiceActivator
    public String hello(File input) throws Exception {
        try(FileInputStream in = new FileInputStream(input)) {
            String name = new String(StreamUtils.copyToByteArray(in));
            return this.helloWorldService.getHelloMessage(name);
        } catch (Exception e) {
            throw e;
        }
    }
}
