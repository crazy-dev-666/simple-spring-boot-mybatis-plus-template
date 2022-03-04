package cn.dev666.simple.template.exception;

import cn.dev666.simple.template.obj.common.ErrorMsg;
import org.springframework.http.ResponseEntity;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    private ErrorInfo code;

    public BusinessException(ErrorInfo code) {
        super(code.getMsg());
        this.code = code;
    }

    public BusinessException(String message, ErrorInfo code) {
        super(message);
        this.code = code;
    }

    public BusinessException(Throwable cause, ErrorInfo code) {
        super(cause);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause, ErrorInfo code) {
        super(message, cause);
        this.code = code;
    }

    public ErrorInfo getExceptionCode() {
        return code;
    }

    public ResponseEntity<ErrorMsg> getResponseEntity() {
        return ErrorMsg.error(code);
    }
}
