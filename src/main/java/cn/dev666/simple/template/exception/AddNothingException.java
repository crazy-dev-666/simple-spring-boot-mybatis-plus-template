package cn.dev666.simple.template.exception;

import cn.dev666.simple.template.enums.CommonErrorInfo;

public class AddNothingException extends BusinessException {

    public AddNothingException() {
        super(CommonErrorInfo.ADD_NOTHING);
    }
}
