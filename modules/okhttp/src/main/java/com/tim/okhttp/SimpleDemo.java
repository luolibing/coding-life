package com.tim.okhttp;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * desc: TODO
 *
 * @author Kroos.luo
 * @since 2020-12-02 08:41
 */
public class SimpleDemo {

    @Test
    public void get() throws IOException {
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .get()
                .build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        try(Response response = call.execute()) {
            System.out.println(response.body().string());
        }
    }

    @Test
    public void post() throws IOException {
        Map<String, Object> body = new HashMap<>(8);
        body.put("name", "luolibing");
        RequestBody requestBody = RequestBody.create(
                JSON.toJSONString(body), MediaType.get("application/json; charset=utf-8"));
        Request post = new Request.Builder()
                .url("http://www.baidu.com")
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(post);
        try(Response response = call.execute()) {
            System.out.println(response.body().string());
        }
    }
}
