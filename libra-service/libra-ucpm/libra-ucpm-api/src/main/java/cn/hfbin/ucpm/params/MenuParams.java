package cn.hfbin.ucpm.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: huangfubin
 * @Description: MenuVo 类
 * @Date: 2021/7/19
 */
@Data
@ApiModel(value = "树结构请求实体")
public class MenuParams {

    /**
     * 业务类型(可做为业务类型区分的扩展字段)
     */
    @ApiModelProperty(value = "业务类型(可做为业务类型区分的扩展字段)")
    private Integer bizType;
}
