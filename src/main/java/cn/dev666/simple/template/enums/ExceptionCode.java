package cn.dev666.simple.template.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    DEFAULT_ERROR(1, "服务异常，请稍后重试"),
    GET_NOTHING(10, "没有找到对应数据，请查看数据是否存在或查询条件是否有误"),
    ADD_NOTHING(11, "操作成功，但未添加数据"),
    UPDATE_NOTHING(12, "操作成功，但未更新数据"),
    DELETE_NOTHING(13, "操作成功，但未删除数据");

    private int code;

    private String msg;
}
