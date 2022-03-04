package cn.dev666.simple.template.advice;

import cn.dev666.simple.template.enums.CommonErrorInfo;
import cn.dev666.simple.template.exception.BusinessException;
import cn.dev666.simple.template.obj.common.ErrorMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class CommonErrorCtrl {

    @ExceptionHandler(value = {HttpMediaTypeException.class})
    public ResponseEntity<ErrorMsg> handleHttpMediaTypeException(HttpServletRequest request) {
        log.error("请求 " + request.getRequestURI() + " 参数格式异常");
        return ErrorMsg.error(CommonErrorInfo.HTTP_MEDIA_TYPE_ERROR);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorMsg> handleHttpRequestMethodNotSupportedException(HttpServletRequest request) {
        log.error("请求 " + request.getRequestURI() + " 方法不支持");
        return ErrorMsg.error(CommonErrorInfo.HTTP_REQUEST_METHOD_NOT_SUPPORTED);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorMsg> handleOtherExceptions(final HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.error("请求 " + request.getRequestURI() + " 参数解析异常", ex);
        return ErrorMsg.error(CommonErrorInfo.HTTP_MESSAGE_NOT_READABLE);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMsg> handleOtherExceptions(final MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("请求 " + request.getRequestURI() + " 参数校验未通过", ex);
        BindingResult bindingResult = ex.getBindingResult();
        String msg;
        if (bindingResult != null && bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            msg = "请求参数校验未通过，" + fieldError.getField() + ":" + fieldError.getDefaultMessage();
        }else {
            msg = ex.getMessage();
        }
        return ErrorMsg.error(CommonErrorInfo.METHOD_ARGUMENT_NOT_VALID, msg);
    }

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<ErrorMsg> handleOtherExceptions(final BindException ex, HttpServletRequest request) {
        log.error("请求 " + request.getRequestURI() + " 参数绑定异常", ex);
        BindingResult bindingResult = ex.getBindingResult();
        String msg;
        if (bindingResult != null && bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            msg = "请求参数绑定异常，" + fieldError.getField() + ":" + fieldError.getDefaultMessage();
        }else {
            msg = ex.getMessage();
        }
        return ErrorMsg.error(CommonErrorInfo.BIND_EXCEPTION, msg);
    }

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ErrorMsg> handleOtherExceptions(final BusinessException ex, HttpServletRequest request) {
        log.error("请求 "+request.getRequestURI()+" 业务出错", ex);
        return ex.getResponseEntity();
    }

    @ExceptionHandler(value = {Throwable.class})
    public ResponseEntity<ErrorMsg> handleOtherExceptions(final Throwable ex, HttpServletRequest request) {
        log.error("请求 "+request.getRequestURI()+" 出错", ex);
        return ErrorMsg.error(CommonErrorInfo.DEFAULT_ERROR);
    }
}