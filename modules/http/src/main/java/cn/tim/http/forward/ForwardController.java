package cn.tim.http.forward;

import cn.tim.http.utils.EnumValidator;
import cn.tim.http.utils.Functions;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by LuoLiBing on 16/8/3.
 * 通信数据转发程序: 代理\网关\隧道
 */
@RestController
@RequestMapping(value = "/forward")
public class ForwardController {

    /**
     * 代理会在响应头中加上Via: 经过的代理服务器
     * 代理的作用 缓存代理\访问控制\获取日志
     * 代理分为两种,一种是是否使用缓存, 另一种是是否会修改报文
     *
     * @return
     */
    @RequestMapping(value = "proxy")
    public Object proxy() {
        return ResponseEntity.ok().header("via", "BJ-Y-NX-103(HIT)").build();
    }

    @PostMapping(value = "/detail")
    public Object detail(@Valid @RequestBody ValidationForm validationForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        System.out.println("detail id = " + validationForm.getId());
        return ResponseEntity.ok().build();
    }
}

class ValidationForm {
    @NotNull(message = "id不能为空")
    private Long id;

    @EnumValidator(message = "不存在的枚举", enumClazz = ROLE.class, valueFunction = ROLE.Function.class)
    private int role;

    public ValidationForm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}

enum ROLE {
    ADMIN(1), EMPLOYEE(2), MANAGER(3);

    private int value;

    ROLE(int value) {
        this.value = value;
    }

    public static class Function implements Functions {
        @Override
        public Object apply(Enum type) {
            return ((ROLE)type).value;
        }
    }
}
