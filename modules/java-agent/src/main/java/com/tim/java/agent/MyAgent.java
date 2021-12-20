package com.tim.java.agent;

import java.lang.instrument.Instrumentation;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Arrays;

/**
 * desc: https://www.ibm.com/developerworks/cn/java/j-lo-jse61/
 * 有意思的java-agent
 *
 *  使用javaagent方式需要添加一个premain方法，premain(arg)或者premain(arg, instrumentation)；后面这个优先级更高
 *  java -javaagent:java-agent-1.0-SNAPSHOT.jar -cp java-agent-1.0-SNAPSHOT.jar com.tim.java.agent.MyAgent
 *  instrumentation.addTransformer可以对classFile进行转换，在前后添加代码，达到agent的效果
 *
 * @author Kroos.luo
 * @since 2020-03-28 23:14
 */
public class MyAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("代理" + agentArgs + ", instrumentation=" + instrumentation);
        instrumentation.addTransformer(new Transformer());
        System.out.println("transformer class");
    }

    public static void main(String[] args) {
        System.out.println(new TransClass().getNumber());
        System.out.println("execute main");
    }
}
