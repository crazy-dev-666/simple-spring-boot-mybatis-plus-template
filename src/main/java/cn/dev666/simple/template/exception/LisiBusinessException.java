package cn.dev666.simple.template.exception;

import cn.dev666.simple.template.obj.common.ErrorMsg;
import org.springframework.http.ResponseEntity;

/**
 * lisi 个人业务异常基类
 */
public class LisiBusinessException extends BusinessException {

    public static final String EMAIL_TO = "koukaiqiang@piccfs.com.cn";

    public LisiBusinessException(ResponseEntity<ErrorMsg> entity) {
        super(entity);
    }

    public LisiBusinessException(String message, ResponseEntity<ErrorMsg> entity) {
        super(message, entity);
    }


    public LisiBusinessException(Throwable cause, ResponseEntity<ErrorMsg> entity) {
        super(cause, entity);
    }


    public LisiBusinessException(String message, Throwable cause, ResponseEntity<ErrorMsg> entity) {
        super(message, cause, entity);
    }
}
