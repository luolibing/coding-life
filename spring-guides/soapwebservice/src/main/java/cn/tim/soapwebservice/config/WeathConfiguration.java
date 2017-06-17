package cn.tim.soapwebservice.config;

import cn.tim.soapwebservice.client.WeatherClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * Created by LuoLiBing on 15/11/5.
 * weathConfiguration配置
 */
@Configuration
public class WeathConfiguration {

    /**
     * jaxb2上下文
     * @return
     */
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("hello.wsdl");
        return marshaller;
    }

    @Bean
    public WeatherClient weatherClient(Jaxb2Marshaller marshaller) {
        WeatherClient client = new WeatherClient();
        client.setDefaultUri("http://ws.cdyne.com/WeatherWS");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
