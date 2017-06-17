package cn.tim.springboot.aspect;

/**
 * User: luolibing
 * Date: 2017/6/1 14:12
 */
public class User implements Defaultable<User> {
    private static long count = 0;

    private Long id = count++;

    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public static long getCount() {
        return count;
    }

    public static void setCount(long count) {
        User.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public User defaultValue() {
        User user = new User();
        user.setId(-1L);
        user.setName("unknow");
        return user;
    }
}
