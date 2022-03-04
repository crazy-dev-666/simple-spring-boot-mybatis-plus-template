package cn.dev666.simple.template.dao;

import cn.dev666.simple.template.obj.ito.user.UserPageableITO;
import cn.dev666.simple.template.obj.model.User;
import cn.dev666.simple.template.obj.oto.user.UserPageOTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 mapper接口
 */
public interface UserDao extends BaseMapper<User>{

   IPage<UserPageOTO> selectPageOto(@Param("page") IPage<UserPageOTO> page, @Param("ito") UserPageableITO ito);
}