package cn.tim

import cn.tim.service.SimpleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder

/**
 * Created by LuoLiBing on 15/8/28.
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class Application implements CommandLineRunner {

    @Autowired
    private SimpleService simpleService;

    @Override
    void run(String... args) throws Exception {
        // 无需密码
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user", "N/A",
                        AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"))
        )
        try {
            simpleService.access()
        } finally {
            SecurityContextHolder.clearContext();
        }

    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args)
    }
}
