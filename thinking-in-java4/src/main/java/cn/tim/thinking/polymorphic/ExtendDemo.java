package cn.tim.thinking.polymorphic;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by LuoLiBing on 16/12/16.
 * 用继承进行设计
 * 优先选择使用组合, "用继承表达行为间的差异, 并用字段表达状态上的变化"
 *
 *
 */
public class ExtendDemo {

    class Actor {
        public void act() {}
    }

    class HappyActor extends Actor {
        @Override
        public void act() {
            System.out.println("HappyActor");
        }
    }

    class SadActor extends Actor {
        @Override
        public void act() {
            System.out.println("SadActor");
        }
    }

    // Stage包含了对actor的引用
    class Stage {
        // actor可以表示为一个状态,  状态可以进行切换, Actor使用继承来表示不同的行为差异
        private Actor actor = new HappyActor();

        // 状态模式, 切换状态
        public void change() {
            actor = new SadActor();
        }

        public void performPlay() {
            actor.act();
        }
    }

    @Test
    public void transmogrify() {
        Stage s = new Stage();
        s.performPlay();
        s.change();
        s.performPlay();
    }


    class AlertStatus {
        public void alert() {

        }
    }

    class NormAlertStatus extends AlertStatus {
        @Override
        public void alert() {
            System.out.println("norm");
        }
    }

    class ErrorAlertStatus extends AlertStatus {
        @Override
        public void alert() {
            System.out.println("error");
        }
    }

    class WarnAlertStatus extends AlertStatus {
        @Override
        public void alert() {
            System.out.println("warn");
        }
    }

    class ShutdownAlertStatus extends AlertStatus {
        @Override
        public void alert() {
            System.out.println("shutdown");
        }
    }

    class StartShip {
        private AlertStatus status = new NormAlertStatus();

        public void change(AlertStatus alertStatus) {
            status = alertStatus;
        }

        public void performAlert() {
            status.alert();
        }
    }

    @Test
    public void startShip() {
        StartShip s = new StartShip();
        s.performAlert();
        s.change(new ErrorAlertStatus());
        s.performAlert();
        s.change(new WarnAlertStatus());
        s.performAlert();
        s.change(new ShutdownAlertStatus());
        s.performAlert();
    }


    /***
     * 纯继承:
     * 导出类具有和基类一样的接口, 可以认为这是一种纯替代, 因为导出类可以完全替代基类. is-a,就是一个类型.
     *
     * 继承扩展:
     * 导出类就像是一个基类, 它有着相同的基本接口,但是它还具有由额外方法实现的其他特性, is-like-a 像一个.
     * is-like-a 向上转型会导致扩展出来的那部分接口无法在基类中使用.
     *
     */

    public static void main(String[] args) throws IOException {
        download("amzn-envelope.xsd");
    }

    public static void download(String xsd) throws IOException {
        String baseUrl = "https://images-na.ssl-images-amazon.com/images/G/01/rainier/help/xsd/release_4_1/ExternalCustomer.xsd";
        String url = "";
        Path path = Paths.get("/Users/anker/Documents/工作/feeder/亚马逊退款/" + xsd);
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(path.toString())) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // handle exception
        }

        if(Files.exists(path)) {
            List<String> schemaStrList = Files.readAllLines(path).stream()
                    .filter(s -> s.contains("xsd:include schemaLocation="))
                    .collect(Collectors.toList());
            System.out.println(schemaStrList);
        }
    }
}
