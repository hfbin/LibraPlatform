package cn.hfbin.ucpm.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: RoleParams 类
 * @Date: 2021/7/6
 */
@Data
@ApiModel(value = "角色菜单实体")
public class RoleMenuParams {

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    /**
     * 角色roleIds
     */
    @ApiModelProperty(value = "角色roleIds")
    private List<Long> roleIds;
    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "菜单ids")
    private List<Long> menuIds;

    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码")
    private String tenantCode;

    /**
     * 租户编码tenantCodes
     */
    @ApiModelProperty(value = "租户编码tenantCodes")
    private List<String> tenantCodes;

}
