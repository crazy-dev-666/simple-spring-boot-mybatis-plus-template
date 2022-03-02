package cn.dev666.simple.template.exception;

import cn.dev666.simple.template.obj.common.ErrorMsg;
import org.springframework.http.ResponseEntity;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    public static final String[] EMAIL_CC = {};

    private ResponseEntity<ErrorMsg> entity;

    public BusinessException(ResponseEntity<ErrorMsg> entity) {
        super(entity.getBody() == null ? null : entity.getBody().getMsg());
        this.entity = entity;
    }

    public BusinessException(String message, ResponseEntity<ErrorMsg> entity) {
        super(message);
        this.entity = entity;
    }

    public BusinessException(Throwable cause, ResponseEntity<ErrorMsg> entity) {
        super(cause);
        this.entity = entity;
    }

    public BusinessException(String message, Throwable cause, ResponseEntity<ErrorMsg> entity) {
        super(message, cause);
        this.entity = entity;
    }

    public ResponseEntity<ErrorMsg> getResponseEntity() {
        return entity;
    }
}
