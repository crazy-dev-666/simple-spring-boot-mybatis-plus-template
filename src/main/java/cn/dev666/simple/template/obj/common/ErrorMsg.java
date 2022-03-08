package cn.dev666.simple.template.obj.common;

import cn.dev666.simple.template.exception.ErrorInfo;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.Nonnull;

@Data
@NoArgsConstructor
@ApiModel(description = "统一响应体")
public class ErrorMsg {

	public static ResponseEntity<ErrorMsg> error(@Nonnull ErrorInfo msg, Object... msgArgs){
		return error(msg.getCode(), msg.getMsg(msgArgs), msg.getStatus());
	}

    public static ResponseEntity<ErrorMsg> error(@Nonnull String message, @Nonnull ErrorInfo msg){
        return error(msg.getCode(), message, msg.getStatus());
    }

	public static ResponseEntity<ErrorMsg> error( int code, @Nonnull String msg, @Nonnull HttpStatus status){
		ErrorMsg errorMsg = new ErrorMsg(code, msg);
		return new ResponseEntity<>(errorMsg, status);
	}

	private ErrorMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@JsonView(BaseView.class)
	@ApiModelProperty("业务异常响应编码")
	private int code;

	@JsonView(BaseView.class)
	@ApiModelProperty("错误消息")
	private String msg;
}
