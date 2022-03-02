package cn.dev666.simple.template.obj.common;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;


@Data
@ApiModel(description = "分页响应结果")
public class Page<T> {

    @ApiModelProperty(value = "总数据量，默认0", example = "0")
    @JsonView(BaseView.class)
    private Long total;

    @ApiModelProperty("当前页数据列表")
    @JsonView(BaseView.class)
    private List<T> list;

    public Page() {
        this.total = 0L;
        this.list = Collections.emptyList();
    }

    public Page(Long total, List<T> list) {
        this.total = total;
        this.list = list;
    }
}
