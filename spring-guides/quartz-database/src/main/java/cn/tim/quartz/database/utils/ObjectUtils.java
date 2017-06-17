package cn.tim.quartz.database.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by LuoLiBing on 16/6/23.
 */
public class ObjectUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static Map parseToMap(String json) throws IOException {
        return objectMapper.readValue(json, Map.class);
    }
}
