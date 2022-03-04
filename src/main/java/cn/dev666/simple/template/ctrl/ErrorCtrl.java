package cn.dev666.simple.template.ctrl;

import cn.dev666.simple.template.enums.CommonErrorInfo;
import cn.dev666.simple.template.obj.common.ErrorMsg;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorCtrl extends AbstractErrorController {


    public ErrorCtrl(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        Map<String, Object> model = Collections
                .unmodifiableMap(getErrorAttributes(request, ErrorAttributeOptions.defaults()));
        response.setStatus(status.value());
        ModelAndView modelAndView = resolveErrorView(request, response, status, model);
        return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
    }


    @RequestMapping
    public ResponseEntity<ErrorMsg> error(HttpServletRequest request){
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        HttpStatus status = getStatus(request);

        if (errorMessage == null){
            return ErrorMsg.error(CommonErrorInfo.DEFAULT_ERROR.getCode(), CommonErrorInfo.DEFAULT_ERROR.getMsg(), status);
        }

        return ErrorMsg.error(CommonErrorInfo.DEFAULT_ERROR.getCode(), (String) errorMessage, status);
    }
}
