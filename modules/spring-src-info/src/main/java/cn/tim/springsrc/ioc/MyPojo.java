package cn.tim.springsrc.ioc;

/**
 * Created by luolibing on 2017/3/26.
 */
public class MyPojo implements BaseInterface {

    public MyPojo() {
        System.out.println("aaaaaa");
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
