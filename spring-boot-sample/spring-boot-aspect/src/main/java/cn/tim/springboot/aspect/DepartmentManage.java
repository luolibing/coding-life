package cn.tim.springboot.aspect;

import org.springframework.stereotype.Service;

/**
 * User: luolibing
 * Date: 2017/6/1 14:15
 */
@Service
public class DepartmentManage {

    public void update() {
        System.out.println("DepartmentManage.update()");
    }

    public void delete() {
        throw new NoPriviliageException();
    }
}
