package cn.tim.springboot.aspect;

import org.springframework.stereotype.Service;

/**
 * User: luolibing
 * Date: 2017/6/1 14:12
 */
@Service
public class UserManage {

    @ThrowingDefaultWrapper(User.class)
    public User create() {
        return new User("luolibing");
    }

    public User find() {
        return new User("liuxiaoling");
    }

    @ThrowingDefaultWrapper(User.class)
    public void delete() {
        throw new RuntimeException();
    }
}
