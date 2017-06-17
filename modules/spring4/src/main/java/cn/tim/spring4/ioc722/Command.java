package cn.tim.spring4.ioc722;

/**
 * Created by LuoLiBing on 16/5/30.
 */
public interface Command {

    void setState(Object commandState);

    Object execute();
}
