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
 * @Description: 账户表
 * @Date: 2021-06-16
 */
@Data
@TableName("ucpm_account")
@ApiModel(value = "账户表")
public class Account extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;
    /**
     * 手机号前缀(可扩展其它国家前缀,默认1大陆+86)
     */
    @ApiModelProperty(value = "手机号前缀(可扩展其它国家前缀,默认1大陆+86)")
    private Integer mobileType;
    /**
     * 账户状态(0-禁用1-启用,如果账号被禁用,账号所关联所有身份将无法登录)
     */
    @ApiModelProperty(value = "账户状态(0-禁用1-启用,如果账号被禁用,账号所关联所有身份将无法登录)")
    private Integer status;
    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码")
    private String tenantCode;
    
}
