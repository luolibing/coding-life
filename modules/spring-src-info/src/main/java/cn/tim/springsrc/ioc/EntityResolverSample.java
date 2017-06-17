package cn.tim.springsrc.ioc;

import org.springframework.beans.factory.xml.BeansDtdResolver;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by luolibing on 2017/4/6.
 */
public class EntityResolverSample implements EntityResolver {

    private EntityResolver dtdResolver = new BeansDtdResolver();

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        System.out.format("publicId=%s, systemId=%s", publicId, systemId);
        return dtdResolver.resolveEntity(publicId, systemId);
    }

    /**
     * <!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN 2.0//EN"
     "http://www.springframework.org/dtd/spring-beans-2.0.dtd">
     * publicId为"-//SPRING//DTD BEAN 2.0//EN"
     * SystemId为http://www.springframework.org/dtd/spring-beans-2.0.dtd
     * @param args
     */
    public static void main(String[] args) throws IOException, SAXException {

        InputSource inputSource = new EntityResolverSample().resolveEntity(
                "-//SPRING//DTD BEAN 2.0//EN",
                "http://www.springframework.org/dtd/spring-beans-2.0.dtd");
        System.out.println(inputSource.getEncoding());
    }
}
