package com.tim.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {

    /**
     * 如果只单独添加了 slf4j-api 是没办法使用log功能，因为slf4j只是一个简单的facade门面，需要绑定具体的log实现
     * slf4j-api会去加载StaticLoggerBinder类，所以log实现需要定义一个"org.slf4j.impl.StaticLoggerBind"类
     *
     * fail-fast，只会报一次，之后不会再报
     * SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
     * SLF4J: Defaulting to no-operation (NOP) logger implementation
     * SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
     *
     * 添加slf4j绑定关系
     * @param args
     */
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(HelloWorld.class);
        logger.info("hello world!!!");

        logger.info("hello world!!! {}", "luolibing");
    }
}
