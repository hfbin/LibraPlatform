package cn.hfbin.plugin.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/5/3 9:54 上午
 * @description:
 */
@Data
@ApiModel(description = "蓝绿灰度路由信息")
public class BlueGreenGrayDto {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("服务类型：1、网关 2、非网关")
    private Integer serviceType;

    @ApiModelProperty("服务名称")
    private String serviceName;

    @ApiModelProperty("描述信息")
    private String description;

    @ApiModelProperty("蓝绿灰度策略配置list")
    private List<Strategy> strategyList;


    @Data
    public static class Strategy{
        @ApiModelProperty("策略类型：0、全局兜底策略 1、兜底蓝绿策略 2、蓝绿策略 3、兜底灰度策略 4、灰度策略")
        private Integer strategyType;

        @ApiModelProperty("条件配置（请求头参数条件）")
        private String condition;

        @ApiModelProperty("路由Id")
        private Long blueGreenRouteId;

        @ApiModelProperty("路由Id, 流量比例（加起来100）")
        private Map<Long, Integer> grayMap;

    }
}
