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
 * @Description: 角色菜单表
 * @Date: 2021-06-16
 */
@Data
@TableName("ucpm_role_menu_ref")
@ApiModel(value = "角色菜单表")
public class RoleMenuRef extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @ApiModelProperty(value = "菜单id")
    private Long menuId;
    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码")
    private String tenantCode;
    
}
