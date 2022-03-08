package cn.dev666.simple.template.advice;

import cn.dev666.simple.template.enums.CommonErrorInfo;
import cn.dev666.simple.template.exception.BusinessException;
import cn.dev666.simple.template.obj.common.ErrorMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public ResponseEntity<ErrorMsg> handleHttpMediaTypeException(HttpMediaTypeException ex, HttpServletRequest request) {
        log.error("请求 {} 参数格式异常：{}", request.getRequestURI(), ex.getMessage());
        return ErrorMsg.error(CommonErrorInfo.HTTP_MEDIA_TYPE_ERROR, request.getContentType());
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorMsg> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        log.error("请求 {} 方法不支持：{}", request.getRequestURI(), request.getMethod());
        return ErrorMsg.error(CommonErrorInfo.HTTP_REQUEST_METHOD_NOT_SUPPORTED, request.getMethod());
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

        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            return ErrorMsg.error(CommonErrorInfo.METHOD_ARGUMENT_NOT_VALID, fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ErrorMsg.error(ex.getMessage(), CommonErrorInfo.METHOD_ARGUMENT_NOT_VALID);
    }

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<ErrorMsg> handleOtherExceptions(final BindException ex, HttpServletRequest request) {
        log.error("请求 " + request.getRequestURI() + " 参数绑定异常", ex);
        BindingResult bindingResult = ex.getBindingResult();

        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            return ErrorMsg.error(CommonErrorInfo.BIND_EXCEPTION, fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ErrorMsg.error(ex.getMessage(), CommonErrorInfo.BIND_EXCEPTION);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<ErrorMsg> handleOtherExceptions(final AuthenticationException ex, HttpServletRequest request) {
        log.error("请求 "+request.getRequestURI()+" 鉴权失败", ex);

        String msg;
        if (ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException){
            msg = "用户名或密码不正确";
        }else if (ex instanceof AccountStatusException){
            msg = "账号状态异常";
        }else {
            return ErrorMsg.error(CommonErrorInfo.DEFAULT_ERROR);
        }

        return ErrorMsg.error(msg, CommonErrorInfo.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<ErrorMsg> handleOtherExceptions(final AccessDeniedException ex, HttpServletRequest request) {
        log.error("请求 "+request.getRequestURI()+" 无权访问", ex);
        return ErrorMsg.error(CommonErrorInfo.ACCESS_DENIED);
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