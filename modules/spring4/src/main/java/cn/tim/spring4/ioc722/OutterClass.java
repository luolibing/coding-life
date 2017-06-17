package cn.tim.spring4.ioc722;

/**
 * Created by LuoLiBing on 16/5/27.
 */
public class OutterClass {

    private InnerClass target;

    public void setTarget(InnerClass target) {
        this.target = target;
    }

    public void execute() {
        this.target.sayHello();
    }
}

class InnerClass {
    private String name;
    private Integer age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void sayHello() {
        System.out.println("name:" + name + ",age:" + age);
    }
}