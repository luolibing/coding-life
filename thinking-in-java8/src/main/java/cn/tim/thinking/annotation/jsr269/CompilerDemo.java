package cn.tim.thinking.annotation.jsr269;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by LuoLiBing on 17/1/14.
 * 编译指令:
 *
 * javac -XprintRounds -processor cn.tim.thinking.annotation.jsr269.MyAnnotationProcessor -classpath "/Users/tim/Documents/samples-tim/thinking-in-java/target/classes" Testing.java
 *
 */
public class CompilerDemo {


    public static void main(String[] args) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> sourcefiles = fileManager.getJavaFileObjects("/Users/tim/Documents/samples-tim/thinking-in-java/src/main/java/cn/tim/thinking/annotation/jsr269/Testing.java");
        Set<String> options = new HashSet<String>();
//        options.add("-processor cn.tim.thinking.annotation.jsr269.MyAnnotationProcessor ");
        compiler.getTask(null, fileManager, null, options, new HashSet<>(), sourcefiles).call();
    }
}
