/*
 *    Copyright [2021] [LibraPlatform of copyright huangfubin]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package cn.hfbin.ucpm.controller;

import cn.hfbin.ucpm.api.MenuApiService;
import cn.hfbin.ucpm.entity.Menu;
import cn.hfbin.ucpm.params.MenuInterfaceParams;
import cn.hfbin.ucpm.params.MenuParams;
import cn.hfbin.ucpm.params.TreeParams;
import cn.hfbin.ucpm.service.AccountIdentityService;
import cn.hfbin.ucpm.service.MenuService;
import cn.hfbin.ucpm.vo.MenuInterfaceVo;
import cn.hfbin.ucpm.vo.PermissionResourceVo;
import cn.hfbin.ucpm.vo.TreeVo;
import cn.hfbin.common.core.api.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 菜单表
 * @Date: 2021-06-16
 */
@RestController
@RequestMapping("/menu" )
@Api(tags = "菜单管理")
public class MenuController implements MenuApiService {

    @Autowired
    private MenuService menuService;

    @Autowired
    private AccountIdentityService accountIdentityService;

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param menu 菜单表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "分页查询数据", notes = "分页查询数据")
    public ResponseData<Page<Menu>> page(Integer pageNo,
                                         Integer pageSize,
                                         Menu menu) {
        return ResponseData.ok(menuService.page(pageNo, pageSize, menu));
    }

    /**
     * 查询所有菜单
     * @param menu 菜单表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "查询所有菜单", notes = "前端自己渲染树结构")
    public ResponseData<List<Menu>> listAll(Menu menu) {
        return ResponseData.ok(menuService.list(menu));
    }

    /**
     * 查询菜单树结构
     * @param menuParams 菜单表
     * @return
     */
    @Override
    @ApiOperation(value = "查询菜单树结构", notes = "查询菜单树结构")
    public ResponseData<List<TreeVo>> tree(MenuParams menuParams) {
        return ResponseData.ok(menuService.tree(menuParams));
    }


    /**
     * 通过id查询菜单表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    public ResponseData<Menu> getById(Long id) {
        return ResponseData.ok(menuService.getById(id));
    }

    /**
     * 新增菜单表
     * @param menu 菜单表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "新增菜单表", notes = "新增菜单表")
    public ResponseData<Integer> save(Menu menu) {
        return ResponseData.ok(menuService.insert(menu));
    }

    /**
     * 通过id查询菜单接口关联
     * @param id id
     * @return
     */
    @Override
    public ResponseData<MenuInterfaceVo> interfaceRefById(Long id) {
        return ResponseData.ok(menuService.interfaceRefById(id));
    }

    /**
     * 菜单接口关联接口
     * @param params 菜单表
     * @return
     */
    @Override
    public ResponseData<Integer> interfaceRefSave(MenuInterfaceParams params) {
        return ResponseData.ok(menuService.interfaceRefSave(params));
    }

    /**
     * 修改菜单表
     * @param menu 菜单表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "更新菜单表", notes = "更新菜单表")
    public ResponseData<Integer> updateById(Menu menu) {
        return ResponseData.ok(menuService.update(menu));
    }

    /**
     * 通过id删除菜单表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id删除菜单表", notes = "通过id删除菜单表")
    public ResponseData<Integer> removeById(Long id) {
        return ResponseData.ok(menuService.deleteById(id));
    }

    /**
     * 查询当前用户菜单
     * @param treeParams
     * @return
     */
    @Override
    @ApiOperation(value = "[auth-service]查询当前用户菜单", notes = "查询当前用户菜单")
    public ResponseData<PermissionResourceVo> queryUserMenu(TreeParams treeParams) {
        return ResponseData.ok(accountIdentityService.selectUserMenu(treeParams));
    }

    /**
     * [平台使用]查询菜单树结构
     * @param menuParams 菜单表
     * @return
     */
    @ApiOperation(value = "[平台使用]查询菜单树结构", notes = "[平台使用]查询菜单树结构")
    @GetMapping("/treeIgnoreTr")
    public ResponseData<List<TreeVo>> treeIgnoreTr(MenuParams menuParams) {
        return ResponseData.ok(menuService.treeIgnoreTr(menuParams));
    }

}
