package com.tim.camel.simple;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
//        from("timer:hello?period={{timer.period}}").routeId("hello")
//                .transform().method("myBean", "saySomething")
//                .filter(simple("${body} contains 'foo'"))
//                .to("log:foo")
//                .end()
//                .to("stream:out");

        from("file://Users/luolibing/Documents/工作文档/商品/数据同步/未命名文件夹/20190327需要同步的productId")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Message message = exchange.getIn();
                        System.out.println(message);
                    }
                });
    }
}
