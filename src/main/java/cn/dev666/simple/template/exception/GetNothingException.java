package cn.dev666.simple.template.exception;

import cn.dev666.simple.template.enums.CommonErrorInfo;

public class GetNothingException extends BusinessException {

    public GetNothingException() {
        super(CommonErrorInfo.GET_NOTHING);
    }
}
