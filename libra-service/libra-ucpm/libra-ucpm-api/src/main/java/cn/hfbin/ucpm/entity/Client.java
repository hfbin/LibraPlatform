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
 * @Description: 客户端管理
 * @Date: 2021-08-27
 */
@Data
@TableName("ucpm_client")
@ApiModel(value = "客户端管理")
public class Client extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 客户端名称
     */
    @ApiModelProperty(value = "客户端名称")
    private String name;
    /**
     * 客户端编码
     */
    @ApiModelProperty(value = "客户端编码")
    private String clientCode;
    /**
     * 授权方式(password,refresh_token,mobile,openId)等
     */
    @ApiModelProperty(value = "授权方式(password,refresh_token,mobile,openId)等")
    private String authGrantTypes;
    /**
     * 账户状态(0-禁用1-启用)
     */
    @ApiModelProperty(value = "账户状态(0-禁用1-启用)")
    private Integer status;
}
