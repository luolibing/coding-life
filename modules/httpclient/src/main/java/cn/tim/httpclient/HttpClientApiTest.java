package cn.tim.httpclient;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by luolibing on 2017/4/6.
 */
public class HttpClientApiTest {

    @Test
    public void test1() {
        HttpClientBuilder.create().setRetryHandler(new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                System.out.println(i);
                e.printStackTrace();

                return false;
            }
        });

        HttpClients.createDefault();
    }
}
