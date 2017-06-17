package cn.tim.thinking.clazz14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Created by LuoLiBing on 16/8/6.
 */
public class LoggerUtils {

    public final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
}
