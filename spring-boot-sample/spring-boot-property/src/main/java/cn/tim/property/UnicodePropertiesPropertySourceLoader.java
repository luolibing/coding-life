package cn.tim.property;

import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 自定义unicodePropertiesPropertySource资源加载
 * 解决乱码问题.
 * spring默认会记载classPath下所有的spring.factories文件，然后加载其中的PropertySourceLoader，
 * 并且按照其中的顺序进行排序@Order，排好序之后，当需要进行配置文件解析时，会依次调用PropertySourceLoader，
 * 第一个能处理的直接处理返回。
 * User: luolibing
 * Date: 2017/6/2 14:29
 */
public class UnicodePropertiesPropertySourceLoader implements PropertySourceLoader {

    @Override
    public String[] getFileExtensions() {
        return new String[]{"properties"};
    }

    @Override
    public PropertySource<?> load(String name, Resource resource, String profile) throws IOException {
        if (profile == null) {
            Properties properties = new Properties();
            properties.load(new InputStreamReader(resource.getInputStream(), "UTF-8"));
            if (!properties.isEmpty()) {
                return new PropertiesPropertySource(name, properties);
            }
        }
        return null;
    }

}