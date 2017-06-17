package cn.tim.activiti

import org.activiti.engine.ProcessEngine
import org.activiti.engine.ProcessEngineConfiguration
import org.activiti.engine.history.HistoricActivityInstance
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration
import org.activiti.engine.impl.form.DateFormType
import org.activiti.engine.impl.form.LongFormType
import org.activiti.engine.impl.form.StringFormType
import java.text.SimpleDateFormat
import java.util.*


/**
 *
 * User: luolibing
 * Date: 2017/6/13 15:18
 */
fun main(args: Array<String>) {
    val cfg = StandaloneProcessEngineConfiguration()
            .setJdbcUrl("jdbc:mysql://localhost:3306/world")
            .setJdbcUsername("root")
            .setJdbcPassword("root")
            .setJdbcDriver("com.mysql.jdbc.Driver")
            .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
    val processEngine = cfg.buildProcessEngine()
    val pName = processEngine.name
    val ver = ProcessEngine.VERSION
    println("ProcessEngine [$pName] Version: [$ver]")

    val repositoryService = processEngine.repositoryService
    val deployment = repositoryService.createDeployment()
            .addClasspathResource("onboarding.bpmn20.xml").deploy()
    val processDefinition = repositoryService.createProcessDefinitionQuery()
            .deploymentId(deployment.id).singleResult()
    System.out.println(
            "Found process definition ["
                    + processDefinition.name + "] with id ["
                    + processDefinition.id + "]")

    val runtimeService = processEngine.runtimeService
    var processInstance = runtimeService
            .startProcessInstanceByKey("onboarding")
    System.out.println("Onboarding process started with process instance id ["
            + processInstance.processInstanceId
            + "] key [" + processInstance.processDefinitionKey + "]")

    val taskService = processEngine.taskService
    val formService = processEngine.formService
    val historyService = processEngine.historyService

    val scanner = Scanner(System.`in`)
    while (processInstance != null && !processInstance.isEnded()) {
        val tasks = taskService.createTaskQuery()
                .taskCandidateGroup("managers").list();
        System.out.println("Active outstanding tasks: [" + tasks.size + "]")
        for (task in tasks) {
            println("Processing Task [" + task.name + "]")
            val variables = HashMap<String, Any>()
            val formData = formService.getTaskFormData(task.id)
            for (formProperty in formData.formProperties) {
                when(formProperty) {
                    is StringFormType -> {
                        println(formProperty.getName() + "?")
                        val value = scanner.nextLine()
                        variables[formProperty.id] = value
                    }

                    is LongFormType -> {
                        println(formProperty.getName() + "? (Must be a whole number)");
                        val value = scanner.nextLine().toLong()
                        variables[formProperty.id] = value
                    }

                    is DateFormType -> {
                        System.out.println(formProperty.getName() + "? (Must be a date m/d/yy)");
                        val dateFormat = SimpleDateFormat("m/d/yy")
                        val value = dateFormat.parse(scanner.nextLine())
                        variables[formProperty.id] = value
                    }

                    else -> {
                        println("<form type not supported>")
                    }
                }
        }
            taskService.complete(task.id, variables)

            var endActivity: HistoricActivityInstance ?= null
            val activities =
            historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstance.id).finished()
                    .orderByHistoricActivityInstanceEndTime().asc()
                    .list()
            for (activity in activities) {
            if (activity.activityType == "startEvent") {
                System.out.println("BEGIN " + processDefinition.name
                        + " [" + processInstance.processDefinitionKey
                        + "] " + activity.startTime)
            }
            if (activity.activityType == "endEvent") {
                // Handle edge case where end step happens so fast that the end step
                // and previous step(s) are sorted the same. So, cache the end step
                //and display it last to represent the logical sequence.
                endActivity = activity
            } else {
                System.out.println("-- " + activity.activityName
                        + " [" + activity.activityId + "] "
                        + activity.durationInMillis + " ms");
            }
        }
            if (endActivity != null) {
                System.out.println("-- " + endActivity.getActivityName()
                        + " [" + endActivity.activityId + "] "
                        + endActivity.getDurationInMillis() + " ms");
                System.out.println("COMPLETE " + processDefinition.getName() + " ["
                        + processInstance.getProcessDefinitionKey() + "] "
                        + endActivity.getEndTime());
            }
        }
        // Re-query the process instance, making sure the latest state is available
        processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstance.getId()).singleResult();
    }
    scanner.close()
}