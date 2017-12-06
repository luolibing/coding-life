package cn.tim.soapwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by LuoLiBing on 15/11/5.
 * spring web service JAXB2 客户端程序
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    CommandLineRunner lookup(final WeatherClient weatherClient) {
//        // java8闭包
//        return args -> {
//            String zipCode = "94304";
//            GetCityForecastByZIPResponse response = weatherClient.getCityForecastByZIPResponse(zipCode);
//            weatherClient.printResponse(response);
//        };
//    }
}
