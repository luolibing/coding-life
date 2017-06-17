package cn.tim.github.webhook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * github，gitlab都提供了webhook钩子以供外部对git事件进行扩展，例如CI，自动发布等等功能都基于webhook实现
 * webhook原理，就是事先在github的某个项目配置上webhook的回调地址以及关注的事件，为了安全考虑也可以配置一个Secret。
 * 以供被回调端验证请求来源是否正确。
 * 当事件发生时，github会调用事先预留下的webhook回调地址，发送一个POST请求。
 * 这个时候咋们就可以在这个回调接口上写一些ci脚本，或者通知了。
 *
 * 由于需要外网地址，如果没有外网域名或者地址，也可以使用ngrok这种外网代理来实现。
 *
 * webhook
 * 1 用户自定义的网络回调，定义一个“钩子”去调用另外一个平台
 * 2 从一个平台传输数据到另外一个平台。
 * User: luolibing
 * Date: 2017/5/17 15:49
 */
@SpringBootApplication
public class WebHookApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebHookApplication.class, args);
    }
}
