package cn.tim.thinking.clazz14.nullable;


/**
 * Created by LuoLiBing on 16/10/8.
 */
public class Position {

    private String title;

    private NullableDemo.Person person;

    public Position(String jobTitle, NullableDemo.Person employee) {
        title = jobTitle;
        person = employee;
        if(person == null) {
            person = NullableDemo.NULL;
        }
    }
}
