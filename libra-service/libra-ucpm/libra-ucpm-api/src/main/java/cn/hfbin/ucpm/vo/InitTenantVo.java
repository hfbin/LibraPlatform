package cn.hfbin.ucpm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: huangfubin
 * @Description: InitTenantParams 类
 * @Date: 2021/8/20
 */
@Data
@ApiModel(value = "列表返回实体")
public class InitTenantVo {
    /**
     * 身份id
     */
    @ApiModelProperty(value = "身份id")
    private Long identityId;
}
