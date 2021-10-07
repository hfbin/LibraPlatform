package cn.hfbin.ucpm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: MenuVo 类
 * @Date: 2021/7/19
 */
@Data
@ApiModel(value = "响应实体")
public class MenuInterfaceVo {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * ids
     */
    @ApiModelProperty(value = "ids")
    private List<Long> ids;
}
