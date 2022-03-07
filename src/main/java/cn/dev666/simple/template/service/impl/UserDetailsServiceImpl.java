package cn.dev666.simple.template.service.impl;

import cn.dev666.simple.template.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public User loadUserByUsername(String username) {
        cn.dev666.simple.template.obj.model.User user;
        try {
            user = userService.getByUserName(username);
        } catch (Exception e) {
            throw new AuthenticationServiceException("", e);
        }

        if (user == null) {
            throw new UsernameNotFoundException("");
        }

        //初始化权限
        Set<GrantedAuthority> set = new HashSet<>();
        set.add(new SimpleGrantedAuthority("user:add"));
        set.add(new SimpleGrantedAuthority("user:update"));

        if ("admin".equals(user.getUsername())){
            set.add(new SimpleGrantedAuthority("user:delete"));
        }
        return new User(user.getUsername(), user.getPassword(), set);
    }
}
