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


import cn.hfbin.ucpm.entity.Role;
import cn.hfbin.ucpm.entity.RoleGroup;
import cn.hfbin.ucpm.params.RoleMenuParams;
import cn.hfbin.ucpm.params.RoleParams;
import cn.hfbin.ucpm.vo.RoleGroupVo;
import cn.hfbin.ucpm.vo.RoleMenuVo;
import cn.hfbin.ucpm.vo.RoleVo;
import cn.hfbin.common.core.api.ResponseData;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: huangfubin
 * @Description: 角色表 服务调用接口
 * @Date: 2021-06-16
 */
public interface RoleApiService {

    /**
     * 查询角色（包括角色分组）
     * @return ResponseData
     */
    @GetMapping("/tree")
    ResponseData<List<RoleGroupVo>> tree(RoleParams roleParams);

    /**
     * 查询角色列表
     * @return ResponseData
     */
    @GetMapping("/list")
    ResponseData<List<RoleVo>> list(RoleParams roleParams);
    /**
     * 通过id查询角色表
     * @param id id
     * @return ResponseData
     */
    @GetMapping("/{id}")
    ResponseData<Role> getById(@PathVariable("id" ) Long id);

    /**
     * 新增角色表
     * @param role 角色表
     * @return ResponseData
     */
    @PostMapping("/save")
     ResponseData<Integer> save(@RequestBody Role role);

    /**
     * 保存角色菜单权限
     * @param roleMenuParams 角色权限
     * @return ResponseData
     */
    @PostMapping("/save-role-menu")
    ResponseData<Integer> saveRoleMenu(@RequestBody RoleMenuParams roleMenuParams);

    /**
     * 查询角色菜单权限
     * @param roleId 角色id
     * @return ResponseData
     */
    @GetMapping("/menu/{roleId}")
    ResponseData<RoleMenuVo> getMenu(@PathVariable("roleId" ) Long roleId);

    /**
     * 修改角色表
     * @param role 角色表
     * @return ResponseData
     */
    @PutMapping("/edit")
    ResponseData<Integer> updateById(@RequestBody Role role);

    /**
     * 通过id删除角色表
     * @param id id
     * @return ResponseData
     */
    @DeleteMapping("delete/{id}")
    ResponseData<Integer> removeById(@PathVariable("id") Long id);

    /**
     * 新增角色组
     * @param roleGroup 角色组
     * @return ResponseData
     */
    @PostMapping("/group/save")
    ResponseData<Integer> saveGroup(@RequestBody RoleGroup roleGroup);

    /**
     * 修改角色组
     * @param roleGroup 角色组
     * @return ResponseData
     */
    @PutMapping("/group/edit")
    ResponseData<Integer> updateGroupById(@RequestBody RoleGroup roleGroup);

    /**
     * 通过id删除角色组
     * @param id id
     * @return ResponseData
     */
    @DeleteMapping("/group/delete/{id}")
    ResponseData<Integer> removeGroupById(@PathVariable("id") Long id);

    /**
     * 通过id查询角色组
     * @param id id
     * @return ResponseData
     */
    @GetMapping("/group/{id}")
    ResponseData<RoleGroup> getGroupById(@PathVariable("id") Long id);

}
