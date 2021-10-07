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

package cn.hfbin.base.entity;


import cn.hfbin.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: huangfubin
 * @Description: 系统操作日志表
 * @Date: 2021-08-31
 */
@Data
@TableName("base_opt_log")
@ApiModel(value = "系统操作日志表")
public class OptLog implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 日志类型
     */
    @ApiModelProperty(value = "日志类型")
    private Integer optType;
    /**
     * 操作行为
     */
    @ApiModelProperty(value = "操作行为")
    private Integer optBehavior;
    /**
     * IP
     */
    @ApiModelProperty(value = "IP")
    private String ip;
    /**
     * IP解析地址
     */
    @ApiModelProperty(value = "IP解析地址")
    private String location;
    /**
     * 客户端编码
     */
    @ApiModelProperty(value = "客户端编码")
    private String clientCode;
    /**
     * 请求地址
     */
    @ApiModelProperty(value = "请求地址")
    private String reqUrl;
    /**
     * 浏览器信息
     */
    @ApiModelProperty(value = "浏览器信息")
    private String userAgent;
    /**
     * 请求类型(GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;等)
     */
    @ApiModelProperty(value = "请求类型(GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;等)")
    private String httpMethod;
    /**
     * 操作请求参数
     */
    @ApiModelProperty(value = "操作请求参数")
    private String reqParam;
    /**
     * 操作响应结果
     */
    @ApiModelProperty(value = "操作响应结果")
    private String resInfo;
    /**
     * 操作响应结果
     */
    @ApiModelProperty(value = "操作响应结果")
    private Integer resStatus;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码")
    private String tenantCode;


    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
