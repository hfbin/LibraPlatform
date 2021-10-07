package cn.hfbin.ucpm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: huangfubin
 * @Description: RouterMetaVo 类
 * @Date: 2021/6/25
 */
@Data
@ApiModel(value = "菜单响应对象")
public class RouterMetaVo implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "图标")
    private String icon = "";
    @ApiModelProperty(value = "面包屑")
    private Boolean breadcrumb = true;
    @ApiModelProperty(value = "页面缓存 如果设置为true，页面将不会被缓存(默认为false)")
    private Boolean noCache = false;
}