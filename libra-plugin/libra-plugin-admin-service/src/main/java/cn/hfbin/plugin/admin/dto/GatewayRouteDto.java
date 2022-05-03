package cn.hfbin.plugin.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/5/4 12:24 上午
 * @description:
 */
@Data
@ApiModel(description = "网关动态路由信息")
public class GatewayRouteDto{

    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("网关名称")
    private String gatewayName;

    @ApiModelProperty("目标地址")
    private String uri;

    @ApiModelProperty("服务名称")
    private String serviceName;

    @ApiModelProperty("断言器")
    private List<String> predicates;

    @ApiModelProperty("过滤器")
    private List<String> filters;

    @ApiModelProperty("优先级")
    private int order;

    @ApiModelProperty("元数据")
    private Map<String, Object> metadata;

    @ApiModelProperty("描述")
    private String description;
}
