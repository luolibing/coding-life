package cn.tim.activiti.myprocess;

import cn.tim.activiti.entity.Report;
import org.springframework.stereotype.Component;

/**
 * Created by luolibing on 2017/6/20.
 */
@Component
public class VerifyReportListener {

    public void verifyReport(Report report) {
        System.out.println("received report " + report);
    }
}
