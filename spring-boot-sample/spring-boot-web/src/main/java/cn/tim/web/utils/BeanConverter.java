package cn.tim.web.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.cglib.beans.BeanCopier;

/**
 * User: luolibing
 * Date: 2018/3/23 18:37
 */
public class BeanConverter {

    @Test
    public void convert() throws IllegalAccessException, InstantiationException {
        Person p = new Person(1L, "luo", "libing");
        Class<PersonView> viewClazz = PersonView.class;
        BeanCopier beanCopier = BeanCopier.create(Person.class, viewClazz, false);
        PersonView personView = viewClazz.newInstance();
        beanCopier.copy(p, personView, null);
        System.out.println(personView);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Person {
        private Long id;

        private String firstName;

        private String secondName;
    }

    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
    static class PersonView {
        private Long id;

        private String firstName;

        private String secondName;
    }

    @Test
    public void build() {
//        PersonView.builder().id(1L).firstName("luo").secondName("libing").build();
    }
}
