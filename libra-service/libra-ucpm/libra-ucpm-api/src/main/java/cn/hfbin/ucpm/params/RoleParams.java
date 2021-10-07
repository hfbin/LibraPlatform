package cn.hfbin.ucpm.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Author: huangfubin
 * @Description: RoleParams 类
 * @Date: 2021/7/6
 */
@Data
public class RoleParams {

    /**
     * ids
     */
    @ApiModelProperty(value = "Ids")
    private Set<Long> ids;

    /**
     * tenantCodes
     */
    @ApiModelProperty(value = "tenantCodes")
    private List<String> tenantCodes;

    /**
     * systemDefault
     */
    @ApiModelProperty(value = "systemDefault")
    private Integer systemDefault;
}
