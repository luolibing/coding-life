package cn.tim.device;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LuoLiBing on 15/9/29.
 */
@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    /**
     * 判断来自不同设备的请求，一般、手机、平板
     * @param device
     * @return
     */
    @RequestMapping("/device")
    public String device(Device device) {
        String deviceType = "unknow";
        if(device.isNormal()) {
            deviceType = "normal";
        } else if(device.isMobile()) {
            deviceType = "mobile";
        } else if(device.isTablet()) {
            deviceType = "tablet";
        }
        return "come from " + deviceType + " browser!";
    }
}
