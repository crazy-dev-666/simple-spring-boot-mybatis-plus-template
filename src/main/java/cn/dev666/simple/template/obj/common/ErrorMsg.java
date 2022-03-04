package cn.dev666.simple.template.obj.common;

import cn.dev666.simple.template.enums.ExceptionCode;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
@ApiModel(description = "统一响应体")
public class ErrorMsg {

	public static ResponseEntity<ErrorMsg> error(@NotNull ExceptionCode msg){
		return error(msg.getCode(), msg.getMsg(), msg.getStatus());
	}

    public static ResponseEntity<ErrorMsg> error(@NotNull ExceptionCode msg, @NotNull String message){
        return error(msg.getCode(), message, msg.getStatus());
    }

    private static ResponseEntity<ErrorMsg> error( int code, @NotNull String msg, @NotNull HttpStatus status){
		assert msg != null;
		assert status != null;

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
