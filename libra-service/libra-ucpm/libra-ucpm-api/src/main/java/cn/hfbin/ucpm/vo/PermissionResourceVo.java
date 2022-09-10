package cn.hfbin.ucpm.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: huangfubin
 * @Description: AuthVo 类
 * @Date: 2021/8/3
 */
@Data
public class PermissionResourceVo {

    /**
     * 菜单权限
     */
    private List<RouterVo> routerVos;

    /**
     * 按钮权限
     */
    private List<String> btnAuths;

    /**
     * 接口权限code
     */
    private List<String> interfaces;

    /**
     * 数据权限
     */
    private Map<String, Objects> dsMap;
}
