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
 * @Description: 角色表
 * @Date: 2021-06-16
 */
@Data
@TableName("ucpm_role")
@ApiModel(value = "角色表")
public class Role extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
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
     * 角色组id
     */
    @ApiModelProperty(value = "角色组id")
    private Long groupId;
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
     * 数据权限(10-全部;20-自定义;30-本级以及子级;40-本级;50-个人), 默认为10
     */
    @ApiModelProperty(value = "数据权限(10-全部;20-自定义;30-本级以及子级;40-本级;50-个人), 默认为10")
    private Integer dsType;
    /**
     * 自定义数据类型部门扩展字段
     */
    @ApiModelProperty(value = "自定义数据类型部门扩展字段")
    private String deptCode;
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
