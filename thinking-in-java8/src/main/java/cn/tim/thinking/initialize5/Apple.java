package cn.tim.thinking.initialize5;

/**
 * Created by LuoLiBing on 16/8/14.
 */
public class Apple {

    private String name = "apple";

    public String getName() {
        return name;
    }

    /**
     * 将本身this传递过去
     */
    public void eat() {
        Person.peel(this);
    }

    public static void main(String[] args) {
        new Apple().eat();
    }
}
