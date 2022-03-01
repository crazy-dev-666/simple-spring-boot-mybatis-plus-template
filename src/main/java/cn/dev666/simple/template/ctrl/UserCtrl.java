package cn.dev666.simple.template.ctrl;

import cn.dev666.simple.template.convert.UserConvert;
import cn.dev666.simple.template.exception.BusinessException;
import cn.dev666.simple.template.obj.common.Msg;
import cn.dev666.simple.template.obj.common.Page;
import cn.dev666.simple.template.obj.common.oto.IdOTO;
import cn.dev666.simple.template.obj.ito.user.UserModifyITO;
import cn.dev666.simple.template.obj.ito.user.UserPageableITO;
import cn.dev666.simple.template.obj.model.User;
import cn.dev666.simple.template.obj.oto.user.UserOTO;
import cn.dev666.simple.template.obj.oto.user.UserPageOTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 用户 相关接口
 */
@Slf4j
@RestController
@RequestMapping("/api/user/v1/")
@Api(tags = "用户_相关接口")
public class UserCtrl {

    private static final Map<Long, User> USER_MAP = new ConcurrentSkipListMap<>();

    private static final AtomicLong ID_SEQUENCE = new AtomicLong(1);

    @Resource
    private UserConvert userConvert;

    @ApiOperation(value = "查询分页列表")
    @GetMapping("/page")
    public Msg<Page<UserPageOTO>> page(@Validated UserPageableITO ito){
        //TODO
        throw new BusinessException();
    }

    @ApiOperation(value = "根据id查询")
    @GetMapping("/get/{id}")
    public Msg<UserOTO> getOne(@ApiParam(value = "主键", required = true) @PathVariable Long id){
        User data = USER_MAP.get(id);
        if (data == null){
            throw new BusinessException();
        }
        UserOTO result = userConvert.to(data);
        return Msg.ok(result);
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public Msg<IdOTO> add(@Validated @RequestBody UserModifyITO ito){
        User data = userConvert.from(ito);
        long id = ID_SEQUENCE.getAndIncrement();
        data.setId(id);
        USER_MAP.put(id, data);
        return Msg.ok(IdOTO.newObj(id));
    }

    @ApiOperation(value = "更新")
    @PutMapping("/update")
    public Msg update(@Validated(UserModifyITO.Update.class) @RequestBody UserModifyITO ito){
        User data = userConvert.from(ito);
        boolean containsKey = USER_MAP.containsKey(data.getId());
        if (!containsKey){
            throw new BusinessException();
        }
        return Msg.ok();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public Msg delete(@ApiParam(value = "主键", required = true) @PathVariable Long id){
        User user = USER_MAP.remove(id);
        if (user == null){
            throw new BusinessException();
        }
        return Msg.ok();
    }
}