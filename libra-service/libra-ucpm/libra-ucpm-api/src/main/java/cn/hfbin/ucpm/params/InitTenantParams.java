package cn.hfbin.ucpm.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: InitTenantParams 类
 * @Date: 2021/8/20
 */
@Data
@ApiModel(value = "初始化实体")
public class InitTenantParams {

    @ApiModelProperty(value = "菜单ids")
    private List<Long> menuIds;

    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码")
    private String tenantCode;
    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码")
    private String tenantName;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 租户编码tenantCodes
     */
    @ApiModelProperty(value = "租户编码tenantCodes")
    private List<String> tenantCodes;
}
