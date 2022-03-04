package cn.dev666.simple.template.exception;

import cn.dev666.simple.template.enums.CommonErrorInfo;

public class DeleteNothingException extends BusinessException {

    public DeleteNothingException() {
        super(CommonErrorInfo.DELETE_NOTHING);
    }
}
