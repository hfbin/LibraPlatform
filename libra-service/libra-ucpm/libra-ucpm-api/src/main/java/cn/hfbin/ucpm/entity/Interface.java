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
 * @Description: 接口表
 * @Date: 2021-06-16
 */
@Data
@TableName("ucpm_interface")
@ApiModel(value = "接口表")
public class Interface extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 父ID(为0则一级)
     */
    @ApiModelProperty(value = "父ID(为0则一级)")
    private Long parentId;
    /**
     * 客户端clientCode
     */
    @ApiModelProperty(value = "客户端clientCode,与终端表clientCode关联(平台端/租户管理端/H5/ANDROID_APP/IOS_APP等)")
    private String clientCode;
    /**
     * 权限编码
     */
    @ApiModelProperty(value = "权限编码")
    private String code;
    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称")
    private String name;
    /**
     * 接口路径
     */
    @ApiModelProperty(value = "接口路径")
    private String interfacePath;
    /**
     * 请求方式(0-GET,1-POST,2-PUT,3-DELETE)
     */
    @ApiModelProperty(value = "请求方式(0-GET,1-POST,2-PUT,3-DELETE)")
    private Integer method;
    
}
