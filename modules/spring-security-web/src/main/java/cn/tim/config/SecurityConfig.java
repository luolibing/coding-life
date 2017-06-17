package cn.tim.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * Created by LuoLiBing on 15/8/29.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final static Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    /**
     * 这个方法为override GlobalMethodSecurityConfiguration的方法
     * @return
     */
    /*@Bean
    public AccessDecisionManager accessDecisionManager() {
        return new SimpleAccessManager();
    }*/

    // 设置认证方式，所有用户都需要被认证
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // basic方式
       /* http.authorizeRequests().antMatchers("*").fullyAuthenticated()
                //.accessDecisionManager(accessDecisionManager())
//                .antMatchers("").
                .antMatchers("/static*").permitAll()
                .and().httpBasic();*/
        //httpBasic() basic认证

        // 表单认证,访问控制
        /**
        http.authorizeRequests()
                .antMatchers("/", "/static/**").permitAll()
                .antMatchers("/users/**").hasAuthority("ADMIN")
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .usernameParameter("username")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("remember-me")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .rememberMe();*/

        // 自定义
        http.authorizeRequests()
                .antMatchers("/","/static*", "/error").permitAll()
                .anyRequest().fullyAuthenticated()
                //.accessDecisionManager(accessDecisionManager())
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .usernameParameter("username")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("remember-me")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .rememberMe();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // inmemory
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("employee").and()
                .withUser("admin").password("admin123").roles("employee", "ADMIN");

        // JDBC方式
        /*
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .usersByUsernameQuery("select username,password,enable from users where username=?");
                //.withUser("username").password("password").roles("USER");*/

        // 自行指定方式
        /*auth
                .userDetailsService(userDetailsService);
                //.passwordEncoder(new BCryptPasswordEncoder());*/
    }



}
