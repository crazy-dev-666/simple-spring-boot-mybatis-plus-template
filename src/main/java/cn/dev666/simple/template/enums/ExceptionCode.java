package cn.dev666.simple.template.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    DEFAULT_ERROR(1, "服务异常，请稍后重试"),
    HTTP_MEDIA_TYPE_ERROR(10, "请求的 Content-Type 与接口不匹配，请确认是否和接口文档一致"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(11, "请求方法不支持，请确认是否和接口文档一致"),
    HTTP_MESSAGE_NOT_READABLE(12, "请求参数不能被正确解析，请先确认是否和接口文档一致"),
    METHOD_ARGUMENT_NOT_VALID(13, ""),
    BIND_EXCEPTION(14, ""),

    GET_NOTHING(100, "没有找到对应数据，请查看数据是否存在或查询条件是否有误"),
    ADD_NOTHING(101, "操作成功，但未添加数据"),
    UPDATE_NOTHING(102, "操作成功，但未更新数据"),
    DELETE_NOTHING(103, "操作成功，但未删除数据");

    private int code;

    private String msg;
}
