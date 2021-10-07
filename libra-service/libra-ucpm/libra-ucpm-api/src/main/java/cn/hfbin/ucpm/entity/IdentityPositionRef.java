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
 * @Description: 身份岗位关联表
 * @Date: 2021-06-16
 */
@Data
@TableName("ucpm_identity_position_ref")
@ApiModel(value = "身份岗位关联表")
public class IdentityPositionRef extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 身份id
     */
    @ApiModelProperty(value = "身份id")
    private Long identityId;
    /**
     * 岗位id
     */
    @ApiModelProperty(value = "岗位id")
    private Long positionId;
    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码")
    private String tenantCode;

    
}
