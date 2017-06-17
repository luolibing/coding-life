package cn.tim.mail.service;

import cn.tim.mail.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

/**
 * Created by LuoLiBing on 16/8/31.
 */
@Service
public class SendMailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    @Qualifier(value = "templateMessage")
    private SimpleMailMessage templateMessage;

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    public void sendByTemplate() throws MessagingException {
        // Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("products", Arrays.asList(
                new Product(1L, "luolibing", "待点评"),
                new Product(2L, "liuxiaoling", "已点评"),
                new Product(3L, "luolibing", "待点评")
        ));

        // Prepare message using a Spring helper
        MimeMessage mimeMailMessage = this.mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMailMessage, "UTF-8");
        message.setSubject(templateMessage.getSubject());
        message.setFrom(templateMessage.getFrom());
        message.setTo("luolibing@tim.cn");

        String html = htmlTemplateEngine.process("notanswer_notify", ctx);
        message.setText(html, true);

        // Send email
        this.mailSender.send(mimeMailMessage);
    }


}
