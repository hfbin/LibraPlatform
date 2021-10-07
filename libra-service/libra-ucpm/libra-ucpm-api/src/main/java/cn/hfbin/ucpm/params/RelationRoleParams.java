package cn.hfbin.ucpm.params;

import cn.hfbin.ucpm.enums.RelationTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: RelationRoleParams 类
 * @Date: 2021/8/3
 */
@Data
public class RelationRoleParams {

    private Long id;

    private List<Long> ids;

    private RelationTypeEnum relationTypeEnum;
}
