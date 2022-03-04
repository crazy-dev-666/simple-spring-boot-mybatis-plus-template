package cn.dev666.simple.template.exception;

import cn.dev666.simple.template.enums.ExceptionCode;

public class DeleteNothingException extends BusinessException {

    public DeleteNothingException() {
        super(ExceptionCode.DELETE_NOTHING);
    }
}
