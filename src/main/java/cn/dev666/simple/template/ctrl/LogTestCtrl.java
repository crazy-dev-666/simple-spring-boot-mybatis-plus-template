package cn.dev666.simple.template.ctrl;

import cn.dev666.simple.template.annotation.Anonymous;
import cn.dev666.simple.template.enums.CommonErrorInfo;
import cn.dev666.simple.template.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/api/log")
@Api(tags = "日志测试_相关接口")
public class LogTestCtrl {

    @Resource
    private Environment environment;

    /**
     *  根据请求参数输出不同级别的日志
     */
    @GetMapping
    @ApiOperation(value = "日志输出测试")
    public void log() {
        String profile = Arrays.toString(environment.getActiveProfiles());
        String msg = "env：{}, Hello World!";
        log.trace(msg, profile);
        log.debug(msg, profile);
        log.info(msg, profile);
        log.warn(msg, profile);
        log.error(msg, profile);
        log.error("未知的日志级别: {}", "unknown");
        log.error("未知的错误", new NullPointerException());
    }

    /**
     *  异常事件测试
     */
    @Anonymous
    @GetMapping("/event/test")
    @ApiOperation(value = "异常事件测试")
    public void testEvent() {
        throw new BusinessException(CommonErrorInfo.DEFAULT_ERROR);
    }
}
