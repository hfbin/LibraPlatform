package cn.hfbin.ucpm.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: huangfubin
 * @Description: PositionParams 类
 * @Date: 2021/7/12
 */
@Data
@ApiModel(value = "岗位表")
public class PositionVo {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
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
}
