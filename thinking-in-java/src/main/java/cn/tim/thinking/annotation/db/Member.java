package cn.tim.thinking.annotation.db;

/**
 * Created by LuoLiBing on 17/1/13.
 */
@DBTable(name = "MEMBER")
public class Member {

    // 1 SQLString默认嵌入了@Constraints注解的默认值  2 使用了value快捷方式, 如果程序员得注解中定义了名为value的元素, 可以实在在括号内定义值
    @SQLString(30)
    private String firstName;
    @SQLString(50)
    private String lastName;
    @SQLInteger
    private Integer age;
    @SQLString(value = 30, constraints = @Constraints(primaryKey = true))
    private String handle;
    static int memberCount;

    public String getHandle() {
        return handle;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Member{" +
                "handle='" + handle + '\'' +
                '}';
    }
}
