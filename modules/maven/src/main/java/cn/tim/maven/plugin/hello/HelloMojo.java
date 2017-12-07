package cn.tim.maven.plugin.hello;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * maven 插件
 * User: luolibing
 * Date: 2017/12/7 20:20
 */
@Mojo(name = "hello")
public class HelloMojo extends AbstractMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("HelloMojo");
    }
}
