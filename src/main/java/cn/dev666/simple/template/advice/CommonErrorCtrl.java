package cn.dev666.simple.template.advice;

import cn.dev666.simple.template.enums.ExceptionCode;
import cn.dev666.simple.template.exception.BusinessException;
import cn.dev666.simple.template.exception.LisiBusinessException;
import cn.dev666.simple.template.obj.common.ErrorMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;

/**
 * 系统全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class CommonErrorCtrl {

    private final String mailContext;

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${spring.application.name}")
    private String applicationName;

    {
        ClassPathResource resource = new ClassPathResource("/mailTemplate/exceptionAlarm.html");
        try {
            this.mailContext = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ExceptionHandler(value = {HttpMediaTypeException.class})
    public ResponseEntity<ErrorMsg> handleHttpMediaTypeException() {
        return ErrorMsg.error(ExceptionCode.HTTP_MEDIA_TYPE_ERROR, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorMsg> handleHttpRequestMethodNotSupportedException() {
        return ErrorMsg.error(ExceptionCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorMsg> handleOtherExceptions(final HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.error("请求 " + request.getRequestURI() + " 参数解析异常", ex);
        return ErrorMsg.error(ExceptionCode.HTTP_MESSAGE_NOT_READABLE, HttpStatus.NOT_ACCEPTABLE);
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
        return ErrorMsg.error(ExceptionCode.METHOD_ARGUMENT_NOT_VALID.getCode(), msg, HttpStatus.PRECONDITION_FAILED);
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
        return ErrorMsg.error(ExceptionCode.BIND_EXCEPTION.getCode(), msg, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ErrorMsg> handleOtherExceptions(final BusinessException ex, HttpServletRequest request) {
        log.error("请求 "+request.getRequestURI()+" 出错", ex);
        //TODO 群发邮件
        return ex.getResponseEntity();
    }

    @ExceptionHandler(value = {LisiBusinessException.class})
    public ResponseEntity<ErrorMsg> handleOtherExceptions(final LisiBusinessException ex, HttpServletRequest request) {
        log.error("请求 "+request.getRequestURI()+" 出错", ex);
        //TODO 单发邮件给维护人员，并抄送相关人员
        sendMail(ex, request.getRequestURL().toString(), BusinessException.EMAIL_CC, LisiBusinessException.EMAIL_TO);
        return ex.getResponseEntity();
    }

    @ExceptionHandler(value = {Throwable.class})
    public ResponseEntity<ErrorMsg> handleOtherExceptions(final Throwable ex, HttpServletRequest request) {
        log.error("请求 "+request.getRequestURI()+" 出错", ex);
        return ErrorMsg.serverError(ExceptionCode.DEFAULT_ERROR);
    }

    //FIXME 需要注意异常过多，导致邮箱发送过于频繁问题
    @Async
    public void sendMail(Exception ex, String url, String[] cc, String... to) {
        long start = System.currentTimeMillis();
        try {
            log.info("start send exception（{}） mail to {} ", ex.getClass().getSimpleName(), Arrays.toString(to));
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("系统出现异常：" + ex.getClass().getName());
            String context = mailContext.replace("${serverIp}", getIP())
                    .replace("${applicationName}", applicationName)
                    .replace("${requestURL}", url)
                    .replace("${exceptionMsg}", getStackTrace(ex));
            helper.setText(context, true);
            helper.setFrom(mailFrom);
            helper.setSentDate(new Date());
            if (cc != null && cc.length > 0){
                helper.setCc(cc);
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }finally {
            log.info("end send mail, cost time {} ms ", System.currentTimeMillis() - start);
        }
    }

    /**
     * 获取堆栈信息
     */
    private String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }

    private String getIP() {
        try {
           for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                                    interfaces.hasMoreElements();) {

                NetworkInterface ifc = interfaces.nextElement();
                if (ifc.isUp()) {
                    for (Enumeration<InetAddress> addresses = ifc.getInetAddresses();
                                                addresses.hasMoreElements();) {

                        InetAddress address = addresses.nextElement();
                        if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                            log.trace("Found non-loopback interface: " + ifc.getDisplayName());
                            return address.getHostAddress();
                        }
                    }
                }
            }
        }catch (IOException ex) {
            log.error("Cannot get first non-loopback address", ex);
        }

        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("Unable to retrieve localhost");
        }
        return "unknown";
    }
}