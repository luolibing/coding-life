package cn.tim.springsrc.aop.proxy;

/**
 * Created by luolibing on 2017/6/11.
 */
public class UserServiceImpl implements UserService {
    @Override
    public void add() {
        System.out.println("******* add User ********");
    }
}
