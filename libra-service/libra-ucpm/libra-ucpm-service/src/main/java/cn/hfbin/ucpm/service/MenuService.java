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

package cn.hfbin.ucpm.service;

import cn.hfbin.ucpm.entity.Menu;
import cn.hfbin.ucpm.params.MenuInterfaceParams;
import cn.hfbin.ucpm.params.MenuParams;
import cn.hfbin.ucpm.vo.MenuInterfaceVo;
import cn.hfbin.ucpm.vo.TreeVo;
import cn.hfbin.ucpm.vo.RouterVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 菜单表
 * @Date: 2021-06-25
 */
public interface MenuService {
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param menu
     * @return
     */
    Page<Menu> page(Integer pageNo, Integer pageSize, Menu menu);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Menu getById(Long id);

    /**
     * 新增数据
     * @param menu
     * @return
     */
    int insert(Menu menu);

    /**
     * 更新数据(为null的字段不会更新)
     * @param menu
     * @return
     */
    int update(Menu menu);

    /**
     * 更新数据
     * @param menu
     * @return
     */
    int updateSomeColumnById(Menu menu);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 查询菜单
     * @return
     */
    List<RouterVo> queryUserMenu();

    /**
     * 查询所有菜单
     * @param menu
     * @return
     */
    List<Menu> list(Menu menu);

    /***
     * 查询菜单树结构
     * @param menuParams
     * @return
     */
    List<TreeVo> tree(MenuParams menuParams);

    /**
     * 通过id查询菜单接口关联
     * @param id
     * @return
     */
    MenuInterfaceVo interfaceRefById(Long id);

    /**
     * 菜单接口关联
     * @param params
     * @return
     */
    Integer interfaceRefSave(MenuInterfaceParams params);

    /**
     * [平台使用]查询菜单树结构
     * @param menuParams
     * @return
     */
    List<TreeVo> treeIgnoreTr(MenuParams menuParams);
}
