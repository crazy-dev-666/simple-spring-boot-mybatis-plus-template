package cn.dev666.simple.template.exception;

import cn.dev666.simple.template.enums.CommonErrorInfo;

public class UpdateNothingException extends BusinessException {

    public UpdateNothingException() {
        super(CommonErrorInfo.UPDATE_NOTHING);
    }
}
