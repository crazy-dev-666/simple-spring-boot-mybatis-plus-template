package cn.dev666.simple.template.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/api/log")
@Api(tags = "日志测试_相关接口")
public class LogTestCtrl {

    @Value("#{environment.activeProfiles}")
    private String[] env;

    /**
     *  根据请求参数输出不同级别的日志
     */
    @GetMapping
    @ApiOperation(value = "日志输出测试")
    public void log(String level) {
        String msg = "env：{}, Hello World!";
        String profile = Arrays.toString(env);
        try {
            switch (level){
                case "trace": log.trace(msg, profile); break;
                case "debug": log.debug(msg, profile); break;
                case "info": log.info(msg, profile); break;
                case "warn": log.warn(msg, profile); break;
                case "error": log.error(msg, profile); break;
                default: log.error("未知的日志级别: {}", level); break;
            }
        }catch (NullPointerException e){
            log.error("日志级别为空", e);
        }
    }
}
