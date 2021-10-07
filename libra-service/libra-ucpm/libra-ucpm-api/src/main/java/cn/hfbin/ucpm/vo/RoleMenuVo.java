package cn.hfbin.ucpm.vo;

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
public class RoleMenuVo {

    /**
     * 系统默认数据(0-可操作,1-不可操作)
     */
    @ApiModelProperty(value = "系统默认数据(0-可操作,1-不可操作)")
    private Integer systemDefault;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "菜单ids")
    private List<Long> menuIds;
}
