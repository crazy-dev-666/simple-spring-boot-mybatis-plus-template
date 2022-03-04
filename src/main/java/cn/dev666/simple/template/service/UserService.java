package cn.dev666.simple.template.service;

import cn.dev666.simple.template.obj.common.Page;
import cn.dev666.simple.template.obj.ito.user.UserPageableITO;
import cn.dev666.simple.template.obj.model.User;
import cn.dev666.simple.template.obj.oto.user.UserPageOTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户 service接口
 */
public interface UserService extends IService<User>{

    Page<UserPageOTO> page(UserPageableITO ito);
}