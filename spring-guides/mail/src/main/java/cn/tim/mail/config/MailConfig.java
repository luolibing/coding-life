package cn.tim.mail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by LuoLiBing on 16/8/31.
 */
@Configuration
public class MailConfig {

    @Bean
    public SimpleMailMessage templateMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("luolibing@tim.cn");
        simpleMailMessage.setSubject("测试邮件");
        return simpleMailMessage;
    }
}
