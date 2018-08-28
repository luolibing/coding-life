//package cn.tim.aws.swf.workflow;
//
//import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
//import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
//import com.amazonaws.services.simpleworkflow.model.*;
//
//import static cn.tim.aws.swf.workflow.FlowTypes.*;
//
//public class SimpleWorkflow {
//
//    private static final AmazonSimpleWorkflow swf =
//            AmazonSimpleWorkflowClientBuilder.defaultClient();
//
//    public static void registerDomain() {
//        swf.registerDomain(
//                new RegisterDomainRequest()
//                        .withName(DOMAIN).withWorkflowExecutionRetentionPeriodInDays("1")
//        );
//    }
//
//    public static void registerActivityType() {
//        swf.registerActivityType(
//                new RegisterActivityTypeRequest()
//                .withDomain(DOMAIN)
//                .withName(ACTIVITY)
//                .withVersion(ACTIVITY_VERSION)
//                .withDefaultTaskList(new TaskList().withName(TASKLIST))
//                .withDefaultTaskScheduleToStartTimeout("30")
//                .withDefaultTaskStartToCloseTimeout("600")
//                .withDefaultTaskScheduleToCloseTimeout("630")
//                .withDefaultTaskHeartbeatTimeout("10"));
//    }
//
//    public static void registerWorkflowType() {
//        try {
//            System.out.println("** Registering the workflow type '" + WORKFLOW +
//                    "-" + WORKFLOW_VERSION + "'.");
//            swf.registerWorkflowType(new RegisterWorkflowTypeRequest()
//                    .withDomain(DOMAIN)
//                    .withName(WORKFLOW)
//                    .withVersion(WORKFLOW_VERSION)
//                    .withDefaultChildPolicy(ChildPolicy.TERMINATE)
//                    .withDefaultTaskList(new TaskList().withName(TASKLIST))
//                    .withDefaultTaskStartToCloseTimeout("30"));
//        } catch (TypeAlreadyExistsException e) {
//            System.out.println("** Workflow type already exists!");
//        }
//    }
//
//
//    public static void main(String[] args) {
//        registerDomain();
//        registerWorkflowType();
//        registerActivityType();
//    }
//}
