package cn.hfbin.ucpm.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: EmployeeParams 类
 * @Date: 2021/7/7
 */
@Data
@ApiModel(value = "查询员工列表实体")
public class EmployeeQueryParams {
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;
    /**
     * 工号
     */
    @ApiModelProperty(value = "工号")
    private String workNo;
    /**
     * 角色ids
     */
    @ApiModelProperty(value = "角色ids")
    private List<Long> roleListIds;

    /**
     * 岗位id
     */
    @ApiModelProperty(value = "岗位id")
    private Long positionId;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Long deptId;
    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门ids")
    private List<Long> deptIds;

    /**
     * 在职状态(0-在职,1-离职)
     */
    @ApiModelProperty(value = "在职状态(0-在职,1-离职)")
    private Integer positionStatus;

}
