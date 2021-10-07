package cn.hfbin.ucpm.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2021/7/6 11:39 下午
 * @description:
 */
@Data
public class DepartParams {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "keyword")
    private String keyword;

    @ApiModelProperty(value = "Ids")
    private Set<Long> ids;

}
