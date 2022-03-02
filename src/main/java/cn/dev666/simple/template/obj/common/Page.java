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

    public interface DataView extends BaseView {}

    @ApiModelProperty(value = "总数据量，默认0", example = "0")
    @JsonView(DataView.class)
    private Long total;

    @ApiModelProperty("当前页数据")
    @JsonView(DataView.class)
    private List<T> data;

    public Page() {
        this.total = 0L;
        this.data = Collections.emptyList();
    }

    public Page(Long total, List<T> data) {
        this.total = total;
        this.data = data;
    }
}
