package cn.tim.thinking.clazz14.factory;

/**
 * Created by LuoLiBing on 16/9/28.
 */
public class RegisteredFactories {

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++) {
//            System.out.println(Part.createRandom());
            System.out.println(Part.createRandomClass());
        }
    }
}
