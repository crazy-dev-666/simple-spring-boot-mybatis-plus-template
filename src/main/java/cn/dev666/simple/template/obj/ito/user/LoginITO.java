package cn.dev666.simple.template.obj.ito.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "用户登录入参")
public class LoginITO {

    @NotBlank
    @ApiModelProperty("用户姓名")
    private String username;

    @NotBlank
    @ApiModelProperty("密码")
    private String password;

}