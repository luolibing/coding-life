package cn.tim.springsrc.ioc;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by luolibing on 2017/3/30.
 * spring 中对基本资源类型进行了高度抽象资源描述符的接口， 可以是一个文件，或者类路径资源，字节数字，InputStream等等。 Resource返回的是URL或者文件，具体实现根据不同的实现类不一样。
 *
 * WritableResource
 * ContextResource
 * UrlResource
 * ClassPathResource
 * FileSystemResource
 * PathResource
 * ByteArrayResource
 * InputStreamResource
 *
 *
 */
public class ResourceTest {

    @Test
    public void inputStreamResource() throws IOException {
        Resource resource = new ClassPathResource("sample1/mypojo.xml");
        System.out.println("resource description: " + resource.getDescription());
        int size = resource.getInputStream().available();
        System.out.println("byte size = " + size);
    }

    /**
     * InputStreamResource的简单实现
     *
     */
    @Test
    public void inputStreamResourceSimpleImpl() throws IOException {
        String xmlPath = "sample1/mypojo.xml";
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(xmlPath);
        System.out.println("byte size = " + in.available());
    }

    /**
     * FileSystemResource的简单实现。
     * @throws IOException
     */
    @Test
    public void fileSystemResourceImpl() throws IOException {
        InputStream in = new FileInputStream("/etc/hosts");
        System.out.println(in.available());
    }

    @Test
    public void stringTokenizer() {
        String[] array = StringUtils.tokenizeToStringArray("luo,li,bing,liu,xiao,ling;", ",;");
        System.out.println(Arrays.toString(array));
    }
}
