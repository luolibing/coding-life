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


    public static void main(String[] args) throws IOException {
        class Person {
            private int id;

            private String aName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getaName() {
                return aName;
            }

            public void setaName(String aName) {
                this.aName = aName;
            }
        }
        Person person = new Person();
        person.setId(100);
        person.setaName("aaaa");
        String json = objectMapper.writeValueAsString(person);
        Map map = parseToMap(json);
        System.out.println(map);
    }
}
