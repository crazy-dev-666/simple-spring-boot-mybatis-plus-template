package cn.dev666.simple.template.obj.oto.user;

import cn.dev666.simple.template.obj.common.BaseView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@ApiModel(description = "用户 出参")
public class UserOTO{

    public interface SimpleView extends BaseView {}

    @JsonView(SimpleView.class)
    @ApiModelProperty("主键")
    private Long id;

    @JsonView(SimpleView.class)
    @ApiModelProperty("用户名")
    private String username;

    @JsonView(SimpleView.class)
    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("手机号")
    private String phoneNumber;

    @JsonView(SimpleView.class)
    @ApiModelProperty("用户头像")
    private String userImage;

    @ApiModelProperty("删除状态（0：正常，1：删除）")
    private Integer deleteFlag;

    @ApiModelProperty(value="出生日期", example = "2021-01-01")
    private LocalDate birthday;

    @ApiModelProperty(value="上班时间", example = "00:00:01")
    private LocalTime workStartTime;

    @ApiModelProperty(value="下班时间", example = "00:00:01")
    private LocalTime workEndTime;

    @ApiModelProperty("创建人")
    private Long creator;

    @ApiModelProperty("修改人")
    private Long modifier;

    @ApiModelProperty(value="创建时间", example = "2021-01-01 00:00:00")
    private LocalDateTime createTime;

    @ApiModelProperty(value="修改时间", example = "2021-01-01 00:00:00")
    private LocalDateTime modifyTime;

    @ApiModelProperty("邮箱")
    private String email;

}