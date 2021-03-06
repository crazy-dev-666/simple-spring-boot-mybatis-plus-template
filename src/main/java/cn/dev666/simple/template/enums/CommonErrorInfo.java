package cn.dev666.simple.template.enums;

import cn.dev666.simple.template.exception.ErrorInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

@Getter
@AllArgsConstructor
public enum CommonErrorInfo implements ErrorInfo {
    /**
     * 通用错误响应
     */
    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,1, "服务异常，请稍后重试"),

    /**
     * 方法待实现
     */
    NOT_IMPL(HttpStatus.NOT_IMPLEMENTED,2, "方法还未实现"),

    /**
     * 入参格式不匹配
     */
    HTTP_MEDIA_TYPE_ERROR(HttpStatus.NOT_ACCEPTABLE,10, "请求的 Content-Type {0} 与接口不匹配，请确认是否和接口文档一致"),
    /**
     * 入参方法不支持
     */
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(HttpStatus.METHOD_NOT_ALLOWED,11, "请求方法 {0} 不支持，请确认是否和接口文档一致"),
    /**
     * 入参解析失败
     */
    HTTP_MESSAGE_NOT_READABLE(HttpStatus.NOT_ACCEPTABLE,12, "请求参数不能被正确解析，请先确认是否和接口文档一致"),
    /**
     * 入参校验未通过
     */
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.PRECONDITION_FAILED,13, "请求参数校验未通过，{0}:{1}"),
    /**
     * 入参绑定失败
     */
    BIND_EXCEPTION(HttpStatus.PRECONDITION_FAILED,14, "请求参数绑定异常，{0}:{1}"),


    /**
     * 鉴权失败
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,20, ""),
    /**
     * 没有权限操作
     */
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED,21, "无权限进行此操作"),

    /**
     * 单个查询结果为空
     */
    GET_NOTHING(HttpStatus.INTERNAL_SERVER_ERROR,100, "没有找到对应数据，请查看数据是否存在或查询条件是否有误"),
    /**
     * 更新操作成功，没有影响行数
     */
    UPDATE_NOTHING(HttpStatus.INTERNAL_SERVER_ERROR,101, "操作成功，但未更新数据"),
    /**
     * 删除操作成功，没有影响行数
     */
    DELETE_NOTHING(HttpStatus.INTERNAL_SERVER_ERROR,102, "操作成功，但未删除数据"),
    /**
     * 新增操作成功，没有影响行数
     */
    ADD_NOTHING(HttpStatus.INTERNAL_SERVER_ERROR,103, "操作成功，但未新增数据");

    // Http 状态码
    private HttpStatus status;

    // 业务异常状态码
    private int code;

    // 业务异常信息
    private String msg;


    @Override
    public String getMsg(Object... args) {
        if (args.length == 0){
            return msg;
        }
        return MessageFormat.format(msg, args);
    }
}
