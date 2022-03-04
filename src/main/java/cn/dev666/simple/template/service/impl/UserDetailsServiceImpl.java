package cn.dev666.simple.template.service.impl;

import cn.dev666.simple.template.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

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

        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
