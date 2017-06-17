package cn.tim.webhook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * webHook
 * 给客户提供一个可供回调的钩子，以方便跨平台间的灵活交互，以及可扩展性.
 * 1 定义事件
 * 2 定义webhook，绑定事件，调用的方式（1：仅通知，不要求返回值， 2：调用，且期待指定返回值，3：调用，期待返回值，且指定秘钥）
 * 3 事件发生，回调
 *
 * User: luolibing
 * Date: 2017/5/18 12:35
 */
@SpringBootApplication
public class WebHookApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebHookApplication.class, args);
    }
}
