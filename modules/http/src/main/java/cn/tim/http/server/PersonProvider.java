package cn.tim.http.server;

import java.util.*;

/**
 * Created by LuoLiBing on 16/7/28.
 */
public class PersonProvider {

    private static Map<Long, Person> persons = new HashMap<>();

    static {
        persons.put(1L, new Person(1L, "luolibing"));
        persons.put(2L, new Person(1L, "luominghao"));
        persons.put(3L, new Person(1L, "liuxiaoling"));
        persons.put(4L, new Person(1L, "luoaiyun"));
    }

    public static Optional<Person> findOne(Long id) {
        return Optional.ofNullable(persons.get(id));
    }

    public static List<Person> findAll() {
        return new ArrayList<>(persons.values());
    }

    public static Person deleteOne(Long id) {
        return persons.remove(id);
    }

    public static Person addPerson(Person person) {
        return persons.put(person.getId(), person);
    }
}
