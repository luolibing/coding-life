package cn.tim.thinking.initialize5;

import java.util.Arrays;

/**
 * Created by LuoLiBing on 16/8/23.
 */
public class EnumTest {

    public enum Spiciness {
        NOT, MILD, MEDIUM, HOT, FLAMING
    }

    public static void main(String[] args) {
        Spiciness hot = Spiciness.HOT;
        System.out.println(hot);
        // 自动添加了ordinal声明次序
        System.out.println(hot.ordinal());
        // 自动添加了静态方法values
        System.out.println(Arrays.toString(Spiciness.values()));

        switch (hot) {
            case NOT:
                System.out.println("not"); break;
            case MILD:
                System.out.println("mild"); break;
            case MEDIUM:
                break;
            case HOT:
                break;
            case FLAMING:
                break;
        }

        int i = 3;
        switch (i) {
            case 0:
                System.out.println("0");
            case 1:
            case 2:
                System.out.println(2 + "break"); break;
        }
    }
}
