package cn.dev666.simple.template.exception;

import cn.dev666.simple.template.obj.common.ErrorMsg;
import org.springframework.http.ResponseEntity;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    private ErrorInfo code;

    public BusinessException(ErrorInfo code) {
        this(code, code.getMsg());
    }

    public BusinessException(ErrorInfo code, String message) {
        this(code, message, null);
    }

    public BusinessException(ErrorInfo code, Throwable cause) {
        this(code, code.getMsg(), cause);
    }

    public BusinessException(ErrorInfo code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ErrorInfo getExceptionCode() {
        return code;
    }

    public ResponseEntity<ErrorMsg> getResponseEntity() {
        return ErrorMsg.error(code, getMessage());
    }
}
