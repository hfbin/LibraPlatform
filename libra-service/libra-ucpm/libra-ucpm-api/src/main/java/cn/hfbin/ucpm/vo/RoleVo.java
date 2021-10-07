package cn.hfbin.ucpm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: huangfubin
 * @Description: RoleVo 类
 * @Date: 2021/7/6
 */
@Data
@ApiModel(value = "角色响应对象")
public class RoleVo implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    /**
     * 角色code
     */
    @ApiModelProperty(value = "角色code")
    private String roleCode;
    /**
     * 启用标识(1-正常,0-禁用)
     */
    @ApiModelProperty(value = "启用标识(1-正常,0-禁用)")
    private Integer enabled;
    /**
     * 菜单排序(建议菜单之前步调相差10,方便后续菜单排序调整)
     */
    @ApiModelProperty(value = "菜单排序(建议菜单之前步调相差10,方便后续菜单排序调整)")
    private Integer sortNo;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
}
