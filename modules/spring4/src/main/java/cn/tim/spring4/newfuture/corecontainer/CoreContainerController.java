package cn.tim.spring4.newfuture.corecontainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by LuoLiBing on 16/5/17.
 */
@RestController
public class CoreContainerController {

    @Autowired
    List<Container> containerList;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/core/container")
    public List<Container> list() {
        return containerList;
    }

    @RequestMapping(value = "/core/aliasFor")
    public RestTemplate restTemplate() {
        return restTemplate;
    }
}
