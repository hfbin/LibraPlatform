package cn.hfbin.ucpm.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: AuthVo 类
 * @Date: 2021/8/3
 */
@Data
public class MenuResourceVo {

    /**
     * 路由
     */
    private List<RouterVo> routerVos;

    /**
     * 按钮权限
     */
    private List<String> btnAuths;
}
