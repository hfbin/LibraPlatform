package cn.hfbin.ucpm.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Author: huangfubin
 * @Description: PositionParams 类
 * @Date: 2021/7/12
 */
@Data
@ApiModel(value = "岗位表")
public class PositionParams {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "岗位名称")
    private String name;
    /**
     * 业务类型(可做为业务类型区分的扩展字段)
     */
    @ApiModelProperty(value = "业务类型(可做为业务类型区分的扩展字段)")
    private Integer bizType;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * Ids
     */
    @ApiModelProperty(value = "Ids")
    private Set<Long> ids;

}
