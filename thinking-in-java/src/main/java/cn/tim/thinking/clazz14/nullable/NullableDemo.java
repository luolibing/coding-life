package cn.tim.thinking.clazz14.nullable;

/**
 * Created by LuoLiBing on 16/10/8.
 */
public class NullableDemo {

    interface Null {}

    static class Person {
        final String first;
        final String last;
        final String address;

        Person(String first, String last, String address) {
            this.first = first;
            this.last = last;
            this.address = address;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "first='" + first + '\'' +
                    ", last='" + last + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }
    }

    static class NullPerson extends Person implements Null {

        NullPerson() {
            super("null", "null", "null");
        }

        @Override
        public String toString() {
            return "NullPerson{}";
        }
    }

    public final static Person NULL = new NullPerson();
}
