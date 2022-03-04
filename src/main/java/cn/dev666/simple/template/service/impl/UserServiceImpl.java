package cn.dev666.simple.template.service.impl;

import cn.dev666.simple.template.dao.UserDao;
import cn.dev666.simple.template.obj.common.Page;
import cn.dev666.simple.template.obj.ito.user.UserPageableITO;
import cn.dev666.simple.template.obj.model.User;
import cn.dev666.simple.template.obj.oto.user.UserPageOTO;
import cn.dev666.simple.template.service.UserService;
import cn.dev666.simple.template.utils.PageUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户 service接口实现类
 */
@Slf4j
@Service("userService")
@Transactional
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public Page<UserPageOTO> page(UserPageableITO ito) {
         IPage<UserPageOTO> page = PageUtils.newPage(ito);
         page = userDao.selectPageOto(page, ito);
        return PageUtils.newPage(page);
    }
}