package cn.tim.retry.service;

/**
 * Created by LuoLiBing on 16/4/26.
 */
public class Test<K, V, T, M> {

    public <N> N getN(Class<N> k) throws IllegalAccessException, InstantiationException {
        return k.newInstance();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        final Hello n = new Test<>().getN(Hello.class);
        System.out.println(n);
//        System.out.println(new Test().getN(new Hello()).toString());

//        final Class<ArrayList> clazz = new Test<>().getN(ArrayList.class);

    }

    static class Hello {
        @Override
        public String toString() {
            return "Hello{}";
        }
    }
}
