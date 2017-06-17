package cn.tim.jersey;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

/**
 * Created by LuoLiBing on 17/3/15.
 */
@Component
@Path("/worker")
public class WorkFlowResource {

    @Context
    public HttpServletRequest request;

    @Context
    public HttpServletResponse response;

    @Autowired
    private WorkFlowService workFlowService;

    @GET
    @Produces({"application/json"})
    public String message(@QueryParam("name") String name) {
        Person person = new Person();
        person.setName(name);
        return JSON.toJSONString(person);
//        return workFlowService.message() + name;
    }


    class Person {
        private Long id = 1L;

        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
