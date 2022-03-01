package cn.dev666.simple.template.convert;

import cn.dev666.simple.template.obj.ito.user.UserModifyITO;
import cn.dev666.simple.template.obj.model.User;
import cn.dev666.simple.template.obj.oto.user.UserOTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserConvert {

    User from(UserModifyITO o);

    UserOTO to(User o);

    List<UserOTO> to(Collection<User> list);
}
