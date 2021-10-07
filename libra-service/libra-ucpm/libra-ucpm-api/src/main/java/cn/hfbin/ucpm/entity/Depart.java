/*
 *    Copyright [2021] [LibraPlatform of copyright huangfubin]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package cn.hfbin.ucpm.entity;


import cn.hfbin.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: huangfubin
 * @Description: 组织机构表
 * @Date: 2021-06-16
 */
@Data
@TableName("ucpm_depart")
@ApiModel(value = "组织机构表")
public class Depart extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 父ID(为0则一级)
     */
    @ApiModelProperty(value = "父ID(为0则一级)")
    private Long parentId;
    /**
     * 父IDS
     */
    @ApiModelProperty(value = "父IDS")
    private String parentIds;
    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String deptName;
    /**
     * 机构编码
     */
    @ApiModelProperty(value = "部门编码")
    private String deptCode;
    /**
     * 部门类型(1-公司,2-部门)
     */
    @ApiModelProperty(value = "部门类型(10-集团,20-公司,30-部门)")
    private Integer deptType;
    /**
     * 业务类型(可做为业务类型区分的扩展字段)
     */
    @ApiModelProperty(value = "业务类型(可做为业务类型区分的扩展字段)")
    private Integer bizType;
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
    /**
     * 启用标识(1-正常,0-禁用)
     */
    @ApiModelProperty(value = "启用标识(1-正常,0-禁用)")
    private Integer enabled;
    /**
     * 系统默认数据(0-可操作,1-不可操作)
     */
    @ApiModelProperty(value = "系统默认数据(0-可操作,1-不可操作)")
    private Integer systemDefault;
    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码")
    private String tenantCode;
    
}
