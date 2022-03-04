package cn.dev666.simple.template.service.impl;

import cn.dev666.simple.template.ApplicationTests;
import cn.dev666.simple.template.obj.common.Page;
import cn.dev666.simple.template.obj.ito.user.UserPageableITO;
import cn.dev666.simple.template.obj.oto.user.UserPageOTO;
import cn.dev666.simple.template.service.UserService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest extends ApplicationTests {

    @Resource
    private UserService userService;

    @Test
    void page() {
        Page<UserPageOTO> page = userService.page(new UserPageableITO());
        assertNotNull(page);
    }
}