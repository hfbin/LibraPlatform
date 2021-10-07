package cn.hfbin.auth.controller;

import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.ucpm.client.MenuServiceClient;
import cn.hfbin.ucpm.params.TreeParams;
import cn.hfbin.ucpm.vo.MenuResourceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: huangfubin
 * @Description: MenuResourceController 类
 * @Date: 2021/8/9
 */
@RestController
@Api(tags = "权限模块")
public class MenuResourceController {

    @Autowired
    private MenuServiceClient menuServiceClient;

    @GetMapping("/menu-resource")
    @ApiOperation(value = "查询用户资源权限", notes = "查询用户资源权限")
    public ResponseData<MenuResourceVo> menuResource(){
        TreeParams treeParams = new TreeParams();
        return menuServiceClient.queryUserMenu(treeParams);
    }
}
