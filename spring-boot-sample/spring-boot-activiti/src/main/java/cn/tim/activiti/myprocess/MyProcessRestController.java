package cn.tim.activiti.myprocess;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * User: luolibing
 * Date: 2017/6/19 11:07
 */
@RestController
public class MyProcessRestController {

    @Autowired
    private RuntimeService runtimeService;

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/start-my-process", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void startHireProcess(@RequestBody Map<String, String> data) {
        // 查看当前正在执行的流程
        // Context.getExecutionContext().getProcessDefinition().getId();
        String yearMonth = new SimpleDateFormat("yyyyMM").format(new Date());

        Map<String, Object> vars = Collections.<String, Object>singletonMap("yearMonth", yearMonth);
        // startProcess开启任务,传递参数
        runtimeService.startProcessInstanceByKey("myProcess", vars);
    }
}
