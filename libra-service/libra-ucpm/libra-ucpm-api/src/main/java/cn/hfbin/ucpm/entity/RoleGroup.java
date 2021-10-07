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
 * @Description: 角色组
 * @Date: 2021-06-16
 */
@Data
@TableName("ucpm_role_group")
@ApiModel(value = "角色组")
public class RoleGroup extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色组名称
     */
    @ApiModelProperty(value = "角色组名称")
    private String name;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 业务类型(可做为业务类型区分的扩展字段)
     */
    @ApiModelProperty(value = "业务类型(可做为业务类型区分的扩展字段)")
    private Integer bizType;
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
