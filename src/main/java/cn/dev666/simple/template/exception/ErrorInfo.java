package cn.dev666.simple.template.exception;

import org.springframework.http.HttpStatus;

import java.util.Locale;

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
     * @return 业务异常信息，默认 Locale
     */
    String getMsg(Object... args);

    /**
     * @return 业务异常信息，指定 Locale
     */
    String getMsg(Locale locale, Object... args);
}
