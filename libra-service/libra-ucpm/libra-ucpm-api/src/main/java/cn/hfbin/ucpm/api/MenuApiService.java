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

package cn.hfbin.ucpm.api;

import cn.hfbin.ucpm.entity.Menu;
import cn.hfbin.ucpm.params.MenuInterfaceParams;
import cn.hfbin.ucpm.params.MenuParams;
import cn.hfbin.ucpm.params.TreeParams;
import cn.hfbin.ucpm.vo.MenuInterfaceVo;
import cn.hfbin.ucpm.vo.MenuResourceVo;
import cn.hfbin.ucpm.vo.TreeVo;
import cn.hfbin.common.core.api.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: huangfubin
 * @Description: 菜单表 服务调用接口
 * @Date: 2021-06-25
 */
public interface MenuApiService {

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param menu 菜单表
     * @return ResponseData
     */
    @GetMapping("/page")
    ResponseData<Page<Menu>> page(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                                  @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
                                  Menu menu);

    /**
     * 查询所有菜单
     * @param menu 菜单表
     * @return ResponseData
     */
    @GetMapping("/list")
    ResponseData<List<Menu>> listAll(Menu menu);

    /**
     * 查询所有菜单
     * @param menuParams 菜单表
     * @return ResponseData
     */
    @GetMapping("/tree")
    ResponseData<List<TreeVo>> tree(MenuParams menuParams);

    /**
     * 通过id查询菜单表
     * @param id id
     * @return ResponseData
     */
    @GetMapping("/{id}")
    ResponseData<Menu> getById(@PathVariable("id" ) Long id);

    /**
     * 新增菜单表
     * @param menu 菜单表
     * @return ResponseData
     */
    @PostMapping("/save")
     ResponseData<Integer> save(@RequestBody Menu menu);

    /**
     * 通过id查询菜单接口关联
     * @param id id
     * @return ResponseData
     */
    @GetMapping("/interface-ref/{id}")
    ResponseData<MenuInterfaceVo> interfaceRefById(@PathVariable("id" ) Long id);

    /**
     * 菜单接口关联接口
     * @param params 菜单表
     * @return ResponseData
     */
    @PostMapping("/interface-ref/save")
    ResponseData<Integer> interfaceRefSave(@RequestBody MenuInterfaceParams params);

    /**
     * 修改菜单表
     * @param menu 菜单表
     * @return ResponseData
     */
    @PutMapping("/edit")
    ResponseData<Integer> updateById(@RequestBody Menu menu);

    /**
     * 通过id删除菜单表
     * @param id id
     * @return ResponseData
     */
    @DeleteMapping("delete/{id}")
    ResponseData<Integer> removeById(@PathVariable("id") Long id);

    /**
     * 查询用户当前菜单权限
     * @param treeParams
     * @return
     */
    @PostMapping("user/menu")
    ResponseData<MenuResourceVo> queryUserMenu(@RequestBody TreeParams treeParams);

}
