package cn.dev666.simple.template.exception;

import cn.dev666.simple.template.enums.ExceptionCode;
import cn.dev666.simple.template.obj.common.ErrorMsg;
import org.springframework.http.ResponseEntity;

public class UpdateNothingException extends BusinessException {

    public UpdateNothingException() {
        super(ExceptionCode.UPDATE_NOTHING);
    }
}
