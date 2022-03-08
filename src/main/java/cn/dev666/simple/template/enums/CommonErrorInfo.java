package cn.dev666.simple.template.enums;

import cn.dev666.simple.template.exception.ErrorInfo;
import cn.dev666.simple.template.utils.ResourceUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Locale;

@Slf4j
@Getter
public enum CommonErrorInfo implements ErrorInfo {
    /**
     * 通用错误响应
     */
    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,1),

    /**
     * 方法待实现
     */
    NOT_IMPL(HttpStatus.NOT_IMPLEMENTED,2),

    /**
     * 入参格式不匹配
     */
    HTTP_MEDIA_TYPE_ERROR(HttpStatus.NOT_ACCEPTABLE,10),
    /**
     * 入参方法不支持
     */
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(HttpStatus.METHOD_NOT_ALLOWED,11),
    /**
     * 入参解析失败
     */
    HTTP_MESSAGE_NOT_READABLE(HttpStatus.NOT_ACCEPTABLE,12),
    /**
     * 入参校验未通过
     */
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.PRECONDITION_FAILED,13),
    /**
     * 入参绑定失败
     */
    BIND_EXCEPTION(HttpStatus.PRECONDITION_FAILED,14),

    /**
     * 鉴权失败
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,20),
    /**
     * 没有权限操作
     */
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED,21),

    /**
     * 单个查询结果为空
     */
    GET_NOTHING(HttpStatus.INTERNAL_SERVER_ERROR,100),
    /**
     * 更新操作成功，没有影响行数
     */
    UPDATE_NOTHING(HttpStatus.INTERNAL_SERVER_ERROR,101),
    /**
     * 删除操作成功，没有影响行数
     */
    DELETE_NOTHING(HttpStatus.INTERNAL_SERVER_ERROR,102),
    /**
     * 新增操作成功，没有影响行数
     */
    ADD_NOTHING(HttpStatus.INTERNAL_SERVER_ERROR,103);

    public static final String RESOURCE_NAME = "message/common-error-msg";

    // Http 状态码
    private HttpStatus status;

    // 业务异常状态码
    private int code;

    CommonErrorInfo(HttpStatus status, int code) {
        this.status = status;
        this.code = code;
    }

    @Override
    public String getMsg(Object... args) {
        return ResourceUtils.getMsg(RESOURCE_NAME, this.name(), args);
    }

    @Override
    public String getMsg(Locale locale, Object... args) {
        return ResourceUtils.getMsg(RESOURCE_NAME, locale, this.name(), args);
    }

    public static void main(String[] args) {
        String msg = DEFAULT_ERROR.getMsg(Locale.getDefault());
        System.out.println("msg = " + msg);
    }
}
