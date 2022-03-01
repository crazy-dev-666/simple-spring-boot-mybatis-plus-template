package cn.dev666.simple.template.obj.common.oto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "主键 响应参数")
public class IdOTO {

    public static IdOTO newObj(Serializable id){
        return new IdOTO(id);
    }

    @ApiModelProperty("主键")
    private Serializable id;
}
