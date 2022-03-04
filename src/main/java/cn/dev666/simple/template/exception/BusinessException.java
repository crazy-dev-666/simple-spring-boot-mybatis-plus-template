package cn.dev666.simple.template.exception;

import cn.dev666.simple.template.enums.ExceptionCode;
import cn.dev666.simple.template.obj.common.ErrorMsg;
import org.springframework.http.ResponseEntity;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    private ExceptionCode code;

    public BusinessException(ExceptionCode code) {
        super(code.getMsg());
        this.code = code;
    }

    public BusinessException(String message, ExceptionCode code) {
        super(message);
        this.code = code;
    }

    public BusinessException(Throwable cause, ExceptionCode code) {
        super(cause);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause);
        this.code = code;
    }

    public ExceptionCode getExceptionCode() {
        return code;
    }

    public ResponseEntity<ErrorMsg> getResponseEntity() {
        return ErrorMsg.error(code);
    }
}
