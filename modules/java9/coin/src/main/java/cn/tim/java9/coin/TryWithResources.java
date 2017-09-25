package cn.tim.java9.coin;

import org.junit.Test;


/**
 * User: luolibing
 * Date: 2017/9/25 14:50
 */
public class TryWithResources {

    @Test
    public void resource() {
        Person p1 = new Person();
        Person p2 = new Person();

        // java7 8写法
        try(Person r1= p1; Person r2 = p2) {

        } catch (Exception e) {

        }

        // java9写法
        try(p1; p2) {

        } catch (Exception e) {

        }
    }

    @SafeVarargs
    private void say(String...args) {
        String[] i_j = new String[10];
        System.out.println("___");
    }

    class Person implements AutoCloseable {
        @Override
        public void close() throws Exception {

        }
    }

    interface Support {
        default void good() {
            System.out.println("good");
            sayHello();
        }

        // 接口中允许有private的实现方法
        private void sayHello() {
            System.out.println("sayHello");
        }
    }
}
