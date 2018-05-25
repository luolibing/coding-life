package com.tim.mall.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 签名
 * Created by luolibing on 2018/5/20.
 */
public class SignUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String sign(String token, long timestamp, Object...args) {
        List<String> result = new ArrayList<>();
        result.add("token:" + token);
        result.add("timestamp:" + timestamp);
        Stream.of(args).forEach(arg -> result.add(getObjectStr(arg)));
        String resultStr = result.stream().collect(Collectors.joining(","));
        return resultStr;
    }

    private static String getObjectStr(Object obj) {
        if(Objects.isNull(obj)) {
            return "";
        }

        Map<String, Object> map = objectMapper.convertValue(obj, TreeMap.class);
        return map.entrySet()
                .stream()
                .map(entry -> String.format("%s:%s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(","));
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.setId(100L);
//        person.setName("luolibing");
        String objectStr = getObjectStr(person);
        System.out.println(objectStr);

        String xafsdxfds = sign("xafsdxfds", System.currentTimeMillis());
        System.out.println(xafsdxfds);
    }

    @Data
    public static class Person {
        private Long id;

        private String name;
    }
}
