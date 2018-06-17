package com.tim.maven.plugin.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.Set;

@Mojo(name = "hello")
public class HelloMojo extends AbstractMojo {

    /**
     * 提供可扩展的参数
     */
    @Parameter(property = "name", defaultValue = "luolibing")
    private String name;

    /**
     * 项目对象
     */
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("----------------------hello Mojo, my name is " + name + "----------------------");
        Map pluginContext = getPluginContext();
        Set<Map.Entry> entrySet = pluginContext.entrySet();
        for (Map.Entry entry : entrySet) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().getClass());
            System.out.println(entry.getValue());
        }

        System.out.println(project.getBuild().getOutputDirectory());
    }
}
