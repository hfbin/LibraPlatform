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

package cn.hfbin.tenant.entity;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: huangfubin
 * @Description: 多租户信息表
 * @Date: 2021-06-16
 */
@Data
@TableName("tr_tenant")
@ApiModel(value = "多租户信息表")
public class TrTenant {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键id")
    private Long id;
    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码")
    private String tenantCode;
    /**
     * 租户名称
     */
    @ApiModelProperty(value = "租户名称")
    private String tenantName;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginDate;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endDate;
    /**
     * 状态(0-禁用1-启用)
     */
    @ApiModelProperty(value = "状态(0-禁用1-启用)")
    private Integer status;
    /**
     * 身份id
     */
    @ApiModelProperty(value = "身份id")
    private Long identityId;
    /**
     * 菜单模板id
     */
    @ApiModelProperty(value = "菜单模板id")
    private Long menuTemplateId;
    /**
     * 等级(10-试用版;20-基础版;30-VIP版;40-尊贵版)
     */
    @ApiModelProperty(value = "等级(10-试用版;20-基础版;30-VIP版;40-尊贵版)")
    private Integer level;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    @TableField(fill = FieldFill.UPDATE)
    private Long updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * 删除状态(0-正常,1-已删除)
     */
    @ApiModelProperty(value = "删除状态(0-正常,1-已删除)")
    @TableLogic
    private Integer delFlag;
    
}
