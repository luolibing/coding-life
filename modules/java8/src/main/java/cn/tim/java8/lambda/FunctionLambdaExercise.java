package cn.tim.java8.lambda;

import org.junit.Test;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by LuoLiBing on 16/7/11.
 */
public class FunctionLambdaExercise {

    @Test
    public void test1() {
        log("罗立兵");
    }

    public void log(String message) {
        logIf(Level.INFO, () -> true, () -> message);
    }

    public void logIf(Level level, BooleanSupplier condition, Supplier<String> message) {
        Logger logger = Logger.getGlobal();
        if(logger.isLoggable(level) && condition.getAsBoolean()) {
            logger.log(level, message.get());
        }
    }
}
