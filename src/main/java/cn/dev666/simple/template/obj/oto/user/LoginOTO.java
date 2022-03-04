package cn.dev666.simple.template.obj.oto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "用户登录出参")
@NoArgsConstructor
@AllArgsConstructor
public class LoginOTO {

    @ApiModelProperty("会话 id")
    private String sessionId;

}