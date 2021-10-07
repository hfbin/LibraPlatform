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
import java.time.LocalDateTime;

/**
 * @Author: huangfubin
 * @Description: 员工身份信息表
 * @Date: 2021-06-16
 */
@Data
@TableName("ucpm_identity_employee")
@ApiModel(value = "员工身份信息表")
public class IdentityEmployee extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 账号id
     */
    @ApiModelProperty(value = "账号id")
    private Long accountId;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;
    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private LocalDateTime birthday;
    /**
     * 性别(0-默认未知,1-男,2-女)
     */
    @ApiModelProperty(value = "性别(0-默认未知,1-男,2-女)")
    private Integer sex;
    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件")
    private String email;
    /**
     * 工号
     */
    @ApiModelProperty(value = "工号")
    private String workNo;
    /**
     * 证件类型
     */
    @ApiModelProperty(value = "证件类型")
    private String cardType;
    /**
     * 证件号
     */
    @ApiModelProperty(value = "证件号")
    private String cardNo;
    /**
     * 微信号
     */
    @ApiModelProperty(value = "微信号")
    private String weChatNo;
    /**
     * 入职日期
     */
    @ApiModelProperty(value = "入职日期")
    private LocalDateTime entryDate;
    /**
     * 在职状态(0-在职,1-离职)
     */
    @ApiModelProperty(value = "在职状态(0-在职,1-离职)")
    private Integer positionStatus;
    /**
     * 身份来源(1-系统录入,2-邀请注册)
     */
    @ApiModelProperty(value = "身份来源(1-系统录入,2-邀请注册)")
    private Integer sourceType;
    /**
     * 身份状态(0-禁用1-启用)
     */
    @ApiModelProperty(value = "身份状态(0-禁用1-启用)")
    private Integer status;
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
