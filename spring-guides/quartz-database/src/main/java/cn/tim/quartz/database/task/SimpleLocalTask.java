package cn.tim.quartz.database.task;

/**
 * Created by LuoLiBing on 16/6/24.
 */
public class SimpleLocalTask implements Task {

    @Override
    public void execute() {
        System.out.println("simpleLocalTask ");
    }
}