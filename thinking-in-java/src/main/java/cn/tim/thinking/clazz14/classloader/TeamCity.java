package cn.tim.thinking.clazz14.classloader;

/**
 * User: luolibing
 * Date: 2017/6/30 15:39
 */
public class TeamCity {

    private static String load = load();

    public static String load() {
        System.out.println("Teamcity load");
        return "teamCity";
    }

    public Project getProject() {
        return new Project();
    }

    public static void main(String[] args) {
        new TeamCity();
    }
}
