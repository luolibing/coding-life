package cn.tim.activiti.myprocess;

import org.springframework.stereotype.Component;

/**
 * Created by luolibing on 2017/6/20.
 */
@Component
public class WriteReportTaskListener {

    public void writeReport(String yearMonth) {
        System.out.println("开始编写" + yearMonth + "月份的报告!!!");
    }
}
