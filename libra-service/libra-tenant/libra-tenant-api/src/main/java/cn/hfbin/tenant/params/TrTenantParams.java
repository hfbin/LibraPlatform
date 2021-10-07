package cn.hfbin.tenant.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: huangfubin
 * @Description: TrTenantParams 类
 * @Date: 2021/8/17
 */
@Data
public class TrTenantParams {

    private Long id;
    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码")
    private String tenantCode;
    /**
     * 租户名称
     */
    @ApiModelProperty(value = "租户名称")
    private String tenantName;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginDate;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endDate;

    /**
     * 菜单模板id
     */
    @ApiModelProperty(value = "菜单模板id")
    private Long menuTemplateId;
    /**
     * 等级(10-试用版;20-基础版;30-VIP版;40-尊贵版)
     */
    @ApiModelProperty(value = "等级(10-试用版;20-基础版;30-VIP版;40-尊贵版)")
    private Integer level;

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
}
