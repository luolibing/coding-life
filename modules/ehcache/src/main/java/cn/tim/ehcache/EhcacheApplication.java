package cn.tim.ehcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * User: luolibing
 * Date: 2018/3/13 19:31
 */
@SpringBootApplication
public class EhcacheApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EhcacheApplication.class, args);
    }

    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... strings) throws Exception {
        Role role = new Role();
        role.setRoleId(10);
        roleService.getRoleByPublic(role);
        roleService.getRoleByPublic(role);


        roleService.getRoleByPrivate(role);
        roleService.getRoleByFinal(role);
    }
}
