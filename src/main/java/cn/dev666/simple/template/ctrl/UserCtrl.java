package cn.dev666.simple.template.ctrl;

import cn.dev666.simple.template.convert.UserConvert;
import cn.dev666.simple.template.exception.AddNothingException;
import cn.dev666.simple.template.exception.DeleteNothingException;
import cn.dev666.simple.template.exception.GetNothingException;
import cn.dev666.simple.template.exception.UpdateNothingException;
import cn.dev666.simple.template.obj.common.Page;
import cn.dev666.simple.template.obj.common.oto.IdOTO;
import cn.dev666.simple.template.obj.ito.user.UserModifyITO;
import cn.dev666.simple.template.obj.ito.user.UserPageableITO;
import cn.dev666.simple.template.obj.model.User;
import cn.dev666.simple.template.obj.oto.user.UserOTO;
import cn.dev666.simple.template.obj.oto.user.UserPageOTO;
import cn.dev666.simple.template.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用户 相关接口
 */
@Slf4j
@RestController
@RequestMapping("/api/user/v1/")
@Api(tags = "用户_相关接口")
public class UserCtrl {

    @Resource
    private UserConvert userConvert;

    @Resource
    private UserService userService;

    @ApiOperation(value = "查询分页列表")
    @GetMapping("/page")
    public Page<UserPageOTO> page(@Validated UserPageableITO ito){
       return userService.page(ito);
    }

    @ApiOperation(value = "根据id查询")
    @GetMapping("/get/{id}")
    public UserOTO get(@ApiParam(value = "主键", required = true) @PathVariable Long id){
        User data = userService.getById(id);
        if (data == null){
            throw new GetNothingException();
        }
        return userConvert.to(data);
    }

    @ApiOperation(value = "根据id查询简单信息")
    @GetMapping("/getSimple/{id}")
    @JsonView(UserOTO.SimpleView.class)
    public UserOTO getSimple(@ApiParam(value = "主键", required = true) @PathVariable Long id){
        return get(id);
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public IdOTO add(@Validated @RequestBody UserModifyITO ito){
        User data = userConvert.from(ito);
        boolean flag = userService.save(data);
        if (!flag){
            throw new AddNothingException();
        }
        return IdOTO.newObj(data.getId());
    }

    @ApiOperation(value = "更新")
    @PutMapping("/update")
    public void update(@Validated(UserModifyITO.Update.class) @RequestBody UserModifyITO ito){
        User data = userConvert.from(ito);
        boolean flag = userService.updateById(data);
        if (!flag){
            throw new UpdateNothingException();
        }
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public void delete(@ApiParam(value = "主键", required = true) @PathVariable Long id){
        boolean flag = userService.removeById(id);
        if (!flag){
            throw new DeleteNothingException();
        }
    }
}