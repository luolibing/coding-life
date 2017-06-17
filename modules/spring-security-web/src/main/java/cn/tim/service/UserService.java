package cn.tim.service;

import cn.tim.entity.User;
import cn.tim.entity.UserCreateForm;
import org.springframework.security.access.prepost.PostAuthorize;

import java.util.List;

/**
 * Created by LuoLiBing on 15/8/29.
 */
public interface UserService {

    User findByUsername(String username);

    /**
     * 只能返回认证人本身的bean 或者拥有管理员权限
     * @param id
     * @return
     */
    @PostAuthorize("returnObject.username == authentication.name or hasAuthority('ROLE_ADMIN')")
    User getUserById(Long id);

    /**
     * 方法级 hasAuthority  hasRole  hasAnyRole  principal  authentication  permitAll  denyAll  isAnonymous  isRememberMe  isAuthenticated  isFullyAuthenticated
     * @param form
     * @return
     */
    User save(UserCreateForm form);


    List<User> getUserList();

}
