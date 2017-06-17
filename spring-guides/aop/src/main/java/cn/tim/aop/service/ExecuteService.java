package cn.tim.aop.service;

import org.springframework.stereotype.Service;

/**
 * Created by LuoLiBing on 16/1/11.
 */
@Service
public class ExecuteService {

    public void execute() {
        System.out.println("execute service!!!");
    }
}
