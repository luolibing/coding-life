package cn.tim.soapwebservice.client;

import hello.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.text.SimpleDateFormat;

/**
 * Created by LuoLiBing on 15/11/5.
 * WeatherClient 客户端,发送请求的方式很简单，只需要继承webServiceGatewaySupport 然后使用getWebServiceTemplate
 * 使用这个webservice模板来进行发送webservice请求与接收响应
 */
public class WeatherClient extends WebServiceGatewaySupport {

    private final static Logger log = LoggerFactory.getLogger(WeatherClient.class);

    /**
     * 获取天气预报
     * @param zipCode
     * @return
     */
    public GetCityForecastByZIPResponse getCityForecastByZIPResponse(String zipCode) {
        // JAXB request对象
        GetCityForecastByZIP request = new GetCityForecastByZIP();
        request.setZIP(zipCode);

        log.info("Requesting forecast for " + zipCode);
        // 指挥发送请求与接收响应
        return (GetCityForecastByZIPResponse) getWebServiceTemplate()
                .marshalSendAndReceive(
                        "http://wsf.cdyne.com/WeatherWS/Weather.asmx",
                        request,
                        new SoapActionCallback("http://ws.cdyne.com/WeatherWS/GetCityForecastByZIP"));

    }


    /**
     * 打印天气预报
     * @param response
     */
    public void printResponse(GetCityForecastByZIPResponse response) {
        // JAXB response对象
        ForecastReturn result = response.getGetCityForecastByZIPResult();
        if(result.isSuccess()) {
            log.info("Forecast for " + result.getCity());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for(Forecast forecast: result.getForecastResult().getForecast()) {
                Temp temperature = forecast.getTemperatures();
                log.info(String.format("%s %s %s5°-%s°", format.format(forecast.getDate().toGregorianCalendar().getTime()),
                        forecast.getDesciption(), temperature.getMorningLow(), temperature.getDaytimeHigh()));
                log.info("");
            }
        } else {
            log.info("no forecast recevied!");
        }
    }

}
