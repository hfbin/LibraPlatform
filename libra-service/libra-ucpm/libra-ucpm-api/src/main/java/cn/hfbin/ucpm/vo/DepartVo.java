package cn.hfbin.ucpm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2021/7/6 11:26 下午
 * @description:
 */

@Data
@ApiModel(value = "部门响应对象")
public class DepartVo implements Serializable{
    private static final long serialVersionUID = -1L;
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "父id")
    private Long parentId;
    @ApiModelProperty(value = "parentDeptName")
    private String parentDeptName;
    @ApiModelProperty(value = "部门名称")
    private String deptName;
    @ApiModelProperty(value = "部门Code")
    private String deptCode;
    @ApiModelProperty(value = "部门类型(1-公司,2-部门)")
    private Integer deptType;
    @ApiModelProperty(value = "描述")
    private String description;

}
