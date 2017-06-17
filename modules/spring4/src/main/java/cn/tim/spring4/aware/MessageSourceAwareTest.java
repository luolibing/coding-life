package cn.tim.spring4.aware;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Locale;

/**
 * Created by LuoLiBing on 16/6/17.
 */
@Component
public class MessageSourceAwareTest implements MessageSourceAware {

    @Override
    public void setMessageSource(MessageSource messageSource) {
        System.out.println(messageSource);
        Locale locale = LocaleContextHolder.getLocale();
        final String str = messageSource.getMessage("welcome", new Object[] {1,new Date()}, new Locale("USA"));
        System.out.println(str);
    }
}
