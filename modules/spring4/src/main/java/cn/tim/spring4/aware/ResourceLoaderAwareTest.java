package cn.tim.spring4.aware;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Created by LuoLiBing on 16/6/22.
 */
@Component
public class ResourceLoaderAwareTest implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        final Resource resource = resourceLoader.getResource("dao.xml");
        try {
            final File file = resource.getFile();
            System.out.println(file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
