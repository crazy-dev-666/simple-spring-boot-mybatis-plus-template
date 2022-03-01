package cn.dev666.simple.template.obj.ito.user;

import cn.dev666.simple.template.obj.common.Pageable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户 分页入参")
public class UserPageableITO extends Pageable {


}