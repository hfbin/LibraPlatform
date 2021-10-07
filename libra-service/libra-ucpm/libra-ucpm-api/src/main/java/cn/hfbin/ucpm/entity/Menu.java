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
 * @Description: 菜单表
 * @Date: 2021-06-25
 */
@Data
@TableName("ucpm_menu")
@ApiModel(value = "前端路由")
public class Menu extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 父ID(为0则一级)
     */
    @ApiModelProperty(value = "父ID(为0则一级)")
    private Long parentId;
    /**
     * 菜单标题
     */
    @ApiModelProperty(value = "菜单标题")
    private String name;
    /**
     * 菜单路径
     */
    @ApiModelProperty(value = "菜单路径")
    private String path;
    /**
     * 组件
     */
    @ApiModelProperty(value = "组件")
    private String component;
    /**
     * 菜单类型(0-主菜单,1-子菜单,2-按钮权限)
     */
    @ApiModelProperty(value = "菜单类型(0-主菜单,1-子菜单,2-按钮权限)")
    private Integer menuType;
    /**
     * 客户端编码
     */
    @ApiModelProperty(value = "客户端编码")
    private String clientCode;
    /**
     * 业务类型(可做为业务类型区分的扩展字段)
     */
    @ApiModelProperty(value = "业务类型(可做为业务类型区分的扩展字段)")
    private Integer bizType;
    /**
     * 按钮权限编码(前端用来控制是否显示按钮)
     */
    @ApiModelProperty(value = "按钮权限编码(前端用来控制是否显示按钮)")
    private String perms;
    /**
     * 菜单排序(建议菜单之前步调相差10,方便后续菜单排序调整)
     */
    @ApiModelProperty(value = "菜单排序(建议菜单之前步调相差10,方便后续菜单排序调整)")
    private Integer sortNo;
    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    private String icon;
    /**
     * 是否为子节点(1-是, 0-不是)
     */
    @ApiModelProperty(value = "是否为子节点(1-是, 0-不是)")
    private Integer isLeaf;
    /**
     * 是否缓存开启页面缓存(1-是,0-不是)
     */
    @ApiModelProperty(value = "是否缓存开启页面缓存(1-是,0-不是)")
    private Integer keepAlive;
    /**
     * 是否隐藏路由(1-是,0-不是)
     */
    @ApiModelProperty(value = "是否隐藏路由(1-是,0-不是)")
    private Integer hidden;
    /**
     * 是否为外部链接(1-是,0-不是)
     */
    @ApiModelProperty(value = "是否为外部链接(1-是,0-不是)")
    private Integer isOutUrl;
    /**
     * 模块(可作为端区分,管理端/H5等)
     */
    @ApiModelProperty(value = "模块(可作为端区分,管理端/H5等)")
    private Integer module;
    /**
     * 启用标识(1-正常,0-禁用)
     */
    @ApiModelProperty(value = "启用标识(1-正常,0-禁用)")
    private Integer enabled;
    
}
