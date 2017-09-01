package cn.tim.thinking.clazz14.classloader;

/**
 * User: luolibing
 * Date: 2017/6/30 15:40
 */
public class Project {

    private static String word = getWord();

    public static String getWord() {
        System.out.println("Project get World");
        return word;
    }

    public static void main(String[] args) {
        Integer a = 200;
        Integer b = 200;
        System.out.println(a== b);
    }
}
