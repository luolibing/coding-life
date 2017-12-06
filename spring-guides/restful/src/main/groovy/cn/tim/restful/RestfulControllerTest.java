package cn.tim.restful;

import cn.tim.restful.controller.CustomerController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by LuoLiBing on 16/3/26.
 */
@SpringApplicationConfiguration(classes = Application.class)
public class RestfulControllerTest extends BaseTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new CustomerController()).build();
    }

    /**
     * jsonPath写法 https://github.com/jayway/JsonPath
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        String params = "luolibing";
        this.mockMvc.perform(get("/get/" + params/*可以添加参数*/).accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.key", is(params)));
    }
}
