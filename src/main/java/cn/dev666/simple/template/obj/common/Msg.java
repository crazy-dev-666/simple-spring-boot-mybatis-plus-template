package cn.dev666.simple.template.obj.common;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(description = "统一响应体")
public class Msg<T> {

	private static final int OK = 0;
    public static final Integer DEFAULT_ERROR = 1;
	public static final Integer GET_NOTHING = 10;
	public static final Integer ADD_NOTHING = 11;
	public static final Integer UPDATE_NOTHING = 12;
	public static final Integer DELETE_NOTHING = 13;

	public static <T> Msg<T> ok(){
		return new Msg<>(OK, "", null);
	}

	public static <T> Msg<T> ok(T result){
		return new Msg<>(OK, "", result);
	}

	public static <T> Msg<T> ok(String msg, T result){
		return new Msg<>(OK, msg, result);
	}

	private Msg(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	@JsonView(BaseView.class)
	@ApiModelProperty("业务响应编码，0 成功；非0 业务异常编码；")
	private int code;

	@JsonView(BaseView.class)
	@ApiModelProperty("响应消息")
	private String msg;

	@JsonView(BaseView.class)
	@ApiModelProperty("响应数据")
	private T data;
}
