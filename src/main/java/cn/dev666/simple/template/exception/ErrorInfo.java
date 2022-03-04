package cn.dev666.simple.template.exception;

import org.springframework.http.HttpStatus;

/**
 * 错误信息
 */
public interface ErrorInfo {

    /**
     * @return Http 状态码
     */
    default HttpStatus getStatus(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * @return 业务异常状态码
     */
    int getCode();

    /**
     * @return 业务异常信息
     */
    String getMsg();
}
