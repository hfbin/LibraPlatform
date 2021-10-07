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

import cn.hfbin.ucpm.entity.Role;
import cn.hfbin.ucpm.params.RoleMenuParams;
import cn.hfbin.ucpm.params.RoleParams;
import cn.hfbin.ucpm.vo.RoleGroupVo;
import cn.hfbin.ucpm.vo.RoleMenuVo;
import cn.hfbin.ucpm.vo.RoleVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 角色表
 * @Date: 2021-06-16
 */
public interface RoleService {
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param role
     * @return
     */
    Page<Role> page(Integer pageNo, Integer pageSize, Role role);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Role getById(Long id);

    /**
     * 新增数据
     * @param role
     * @return
     */
    int insert(Role role);

    /**
     * 更新数据(为null的字段不会更新)
     * @param role
     * @return
     */
    int update(Role role);

    /**
     * 更新数据
     * @param role
     * @return
     */
    int updateSomeColumnById(Role role);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 查询角色
     * @param roleParams
     * @return
     */
    List<RoleGroupVo> tree(RoleParams roleParams);

    /**
     * 保存角色菜单权限
     * @param roleMenuParams 角色权限
     * @return Integer
     */
    Integer saveRoleMenu(RoleMenuParams roleMenuParams);

    /**
     * [仅提供初始化角色权限使用]保存角色菜单关联关系数据
     * @param roleMenuParams
     * @return
     */
    Integer saveRoleMenuRef(RoleMenuParams roleMenuParams);

    /**
     * 查询角色菜单权限
     * @param roleId 角色id
     * @return List<RoleMenuVo>
     */
    RoleMenuVo getMenu(Long roleId);

    /**
     *
     * @description 根据ids查询角色
     * @param roleList
     * @author huangfubin
     * @date 2021/7/21
     * @return java.util.List<BaseRole>
     */
    List<Role> getByIds(List<Long> roleList);

    /**
     * 查询角色列表
     * @param roleParams
     * @return
     */
    List<RoleVo> list(RoleParams roleParams);

    /**
     * 查询角色列表（可扩展通用）
     * @param roleParams
     * @return
     */
    List<Role> selectList(RoleParams roleParams);


    /**
     * [排除租户条件]删除角色菜单关联关系，排除租户条件
     * @param roleMenuParams
     * @return
     */
    int deleteRoleMenuRefIgnoreTr(RoleMenuParams roleMenuParams);

    /**
     * [排除租户条件]删除角色菜单关联关系，排除租户条件
     * @param roleParams
     * @return
     */
    List<Role> selectListIgnoreTr(RoleParams roleParams);
}
