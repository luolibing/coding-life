package cn.tim.service.currentuser;

import cn.tim.entity.CurrentUser;
import cn.tim.entity.User;
import cn.tim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Created by LuoLiBing on 15/8/31.
 */
@Service
public class BaseUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("用户名为%s没有找到", username));
        }
        return new CurrentUser(user);
    }


    /**
     * 控制用户只能访问自身或者拥有管理员权限可以访问所有用户
     * @param currentUser
     * @param userId
     * @return
     */
    public boolean canAccessUser(CurrentUser currentUser, Long userId) {
        return (currentUser != null)
                && (currentUser.getUser().getAllAuthorities().contains("ROLE_ADMIN") || currentUser.getUser().getId().equals(userId));
    }
}
