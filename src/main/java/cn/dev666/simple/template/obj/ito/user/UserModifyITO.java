package cn.dev666.simple.template.obj.ito.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@ApiModel(description = "用户 编辑入参")
public class UserModifyITO{

    public interface Update extends Default {}

    @NotNull(groups = Update.class)
    @ApiModelProperty("主键")
    private Long id;

    @NotBlank
    @ApiModelProperty("用户姓名")
    private String username;

    @NotBlank
    @ApiModelProperty("真实姓名")
    private String realName;

    @NotBlank
    @ApiModelProperty("手机号")
    private String phoneNumber;

    @NotBlank
    @ApiModelProperty("邮箱")
    private String email;

    @NotBlank
    @ApiModelProperty("用户头像")
    private String userImage;

    @NotNull
    @ApiModelProperty("删除状态（0：正常，1：删除）")
    private Integer deleteFlag;

    @NotNull
    @ApiModelProperty(value="出生日期", example = "2021-01-01")
    private LocalDate birthday;

    @NotNull
    @ApiModelProperty(value="上班时间", example = "00:00:01")
    private LocalTime workStartTime;

    @NotNull
    @ApiModelProperty(value="下班时间", example = "00:00:01")
    private LocalTime workEndTime;

    @NotNull
    @ApiModelProperty("创建人")
    private Long creator;

    @NotNull
    @ApiModelProperty("修改人")
    private Long modifier;
}