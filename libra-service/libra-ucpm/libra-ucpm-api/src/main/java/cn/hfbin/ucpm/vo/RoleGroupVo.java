package cn.hfbin.ucpm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: huangfubin
 * @Description: RoleVo 类
 * @Date: 2021/7/6
 */
@Data
@ApiModel(value = "角色组响应对象")
public class RoleGroupVo implements Serializable {
    private static final long serialVersionUID = -1L;
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色组名称")
    private String name;
    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    private String remark;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色组名称")
    private List<RoleGroupVo> children;

    /**
     * isRoleGroup
     */
    @ApiModelProperty(value = "isRoleGroup")
    private boolean isRoleGroup;

}
