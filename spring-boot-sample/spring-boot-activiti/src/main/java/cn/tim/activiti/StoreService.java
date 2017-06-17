package cn.tim.activiti;

import org.springframework.stereotype.Component;

@Component
public class StoreService {

    // activiti会将启动的参数传递过来
    public void storeResume(Applicant applicant) {
        System.out.println(applicant);
        System.out.println("Storing resume ...");
    }

}
