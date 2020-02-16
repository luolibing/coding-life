package com.tim.properties;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * 自定义converter注解
 * spring boot 2.2可以使用 ConstructorBinding注解
 * @author Kroos.luo
 * @since 2020-02-16 22:01
 */
public class EmployeeConverter implements Converter<String, Employee> {

    public Employee convert(String s) {
        if(!StringUtils.hasText(s) || s.split(",").length < 2) {
            return null;
        }

        String[] array = s.split(",");
        return new Employee(Long.valueOf(array[0]), array[1]);
    }
}
