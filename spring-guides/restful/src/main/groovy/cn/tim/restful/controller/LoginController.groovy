package cn.tim.restful.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by TIM on 2017/3/12.
 */
@RestController
public class LoginController {

    private List<User> userList = new ArrayList<>();

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(String username, String password) {
        if(username.equals("admin") && password.equals("admin")) {
            return new Result(0, "success", ["admin", "manager", "employee"]);
        }
        return new Result(1, "error",  ["admin", "manager", "employee"]);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(String username, String password) {
        userList.add(new User(username, password))
        return new Result(0, "success", ["admin", "manager", "employee"]);
    }

    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public Object userList() {
        return userList;
    }

    class Result {
        int code;
        String msg;
        List<String> roles;

        List<String> getRoles() {
            return roles
        }

        void setRoles(List<String> roles) {
            this.roles = roles
        }

        int getCode() {
            return code
        }

        void setCode(int code) {
            this.code = code
        }

        String getMsg() {
            return msg
        }

        void setMsg(String msg) {
            this.msg = msg
        }

        Result(int code, String msg, List<String> roles) {
            this.code = code
            this.msg = msg
            this.roles = roles
        }
    }

    class User {
        private static Random rand = new Random(47);
        private final static String[] ROLES = ["管理员","员工", "经理"];
        private String username;
        private String password;
        private String role = ROLES[rand.nextInt(ROLES.length)];

        String getRole() {
            return role
        }

        void setRole(String role) {
            this.role = role
        }

        User() {
        }

        User(String username, String password) {
            this.username = username
            this.password = password
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}