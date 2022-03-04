package cn.dev666.simple.template.exception;

import cn.dev666.simple.template.enums.ExceptionCode;

public class GetNothingException extends BusinessException {

    public GetNothingException() {
        super(ExceptionCode.GET_NOTHING);
    }
}
