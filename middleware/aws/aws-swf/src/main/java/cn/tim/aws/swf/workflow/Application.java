package cn.tim.aws.swf.workflow;

import cn.tim.aws.swf.workflow.engine.WorkFlowExecution;
import cn.tim.aws.swf.workflow.model.flow1.Flow1Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by luolibing on 2018/8/28.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private WorkFlowExecution workFlowExecution;

    @Override
    public void run(String... strings) throws Exception {
        workFlowExecution.startup(1L, "start", new Flow1Form());
        workFlowExecution.startup(1L, "end", new Flow1Form());
    }
}
