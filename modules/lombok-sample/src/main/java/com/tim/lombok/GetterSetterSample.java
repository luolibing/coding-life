package com.tim.lombok;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

public class GetterSetterSample {

    /**
     * getter
     * 一般为getF, 当为boolean基础类型时，get为isF
     * 通过value设置访问控制，private 或者 protected
     */
    @Getter(value = AccessLevel.PRIVATE)
    @Setter
    class Person {
        Long id;

        boolean cool;
    }

    @Test
    public void execute() {
        Person p = new Person();
        p.setId(100L);
        p.setCool(true);
        System.out.println(p.isCool());
    }
}
