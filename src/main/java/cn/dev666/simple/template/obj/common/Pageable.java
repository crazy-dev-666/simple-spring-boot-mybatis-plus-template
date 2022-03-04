package cn.dev666.simple.template.obj.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel(description = "分页参数")
@AllArgsConstructor
public class Pageable{

	@ApiModelProperty(value = "每页行数，默认10", example = "10")
	private Integer size = 10;
	@ApiModelProperty(value = "当前页，默认1", example = "1")
	private Integer page = 1;
	@ApiModelProperty(value = "总数", example = "-1")
	private Integer total = -1;

	public Pageable() {
	}
	
	public Pageable(Integer size) {
		this.size = size;
	}

	public Pageable(Integer size, Integer page) {
		this.size = size;
		this.page = page;
	}
}
