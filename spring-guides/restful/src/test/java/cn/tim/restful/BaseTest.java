package cn.tim.restful;

import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by LuoLiBing on 16/3/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class BaseTest {
}
