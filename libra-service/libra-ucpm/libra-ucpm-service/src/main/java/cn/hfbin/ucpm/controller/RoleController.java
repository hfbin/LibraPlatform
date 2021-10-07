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

import cn.hfbin.common.log.annotation.Log;
import cn.hfbin.common.log.enums.LogTypeEnum;
import cn.hfbin.common.log.enums.OptBehaviorEnum;
import cn.hfbin.ucpm.api.RoleApiService;
import cn.hfbin.ucpm.entity.Role;
import cn.hfbin.ucpm.entity.RoleGroup;
import cn.hfbin.ucpm.params.RoleMenuParams;
import cn.hfbin.ucpm.params.RoleParams;
import cn.hfbin.ucpm.service.RoleGroupService;
import cn.hfbin.ucpm.service.RoleService;
import cn.hfbin.ucpm.vo.RoleGroupVo;
import cn.hfbin.ucpm.vo.RoleMenuVo;
import cn.hfbin.ucpm.vo.RoleVo;
import cn.hfbin.common.core.api.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 角色表
 * @Date: 2021-06-16
 */
@RestController
@RequestMapping("/role" )
@Api(tags = "角色管理")
public class RoleController implements RoleApiService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleGroupService roleGroupService;

    /**
     * 查询角色
     * @param roleParams
     * @return
     */
    @Override
    @ApiOperation(value = "查询角色列表（角色组及角色）", notes = "查询角色列表（角色组及角色）")
    public ResponseData<List<RoleGroupVo>> tree(RoleParams roleParams) {
        return ResponseData.ok(roleService.tree(roleParams));
    }

    /**
     * 查询角色列表（不分页）
     * @param roleParams
     * @return
     */
    @Override
    @ApiOperation(value = "查询角色列表（不分页）", notes = "查询角色列表（不分页）")
    public ResponseData<List<RoleVo>> list(RoleParams roleParams) {
        return ResponseData.ok(roleService.list(roleParams));
    }

    /**
     * 通过id查询角色表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    public ResponseData<Role> getById(Long id) {
        return ResponseData.ok(roleService.getById(id));
    }

    /**
     * 新增角色表
     * @param role 角色表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "新增角色表", notes = "新增角色表")
    public ResponseData<Integer> save(Role role) {
        return ResponseData.ok(roleService.insert(role));
    }


    /**
     * 保存角色菜单权限
     * @param roleMenuParams 角色权限
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "保存角色菜单权限", notes = "保存角色菜单权限")
    public ResponseData<Integer> saveRoleMenu(RoleMenuParams roleMenuParams) {
        return ResponseData.ok(roleService.saveRoleMenu(roleMenuParams));
    }

    /**
     * 查询角色菜单权限
     * @param roleId 角色id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "查询角色菜单权限", notes = "查询角色菜单权限")
    public ResponseData<RoleMenuVo> getMenu(Long roleId) {
        return ResponseData.ok(roleService.getMenu(roleId));
    }

    /**
     * 修改角色表
     * @param role 角色表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "更新角色表", notes = "更新角色表")
    public ResponseData<Integer> updateById(Role role) {
        return ResponseData.ok(roleService.update(role));
    }

    /**
     * 通过id删除角色表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id删除角色表", notes = "通过id删除角色表")
    public ResponseData<Integer> removeById(Long id) {
        return ResponseData.ok(roleService.deleteById(id));
    }

    /**
     * 新增角色组
     * @param roleGroup 角色组
     * @return
     */
    @Override
    @ApiOperation(value = "新增角色组", notes = "新增角色组")
    public ResponseData<Integer> saveGroup(RoleGroup roleGroup) {
        return ResponseData.ok(roleGroupService.insert(roleGroup));
    }

    /**
     * 修改角色组
     * @param roleGroup 角色组
     * @return
     */
    @Override
    @ApiOperation(value = "修改角色组", notes = "修改角色组")
    public ResponseData<Integer> updateGroupById(RoleGroup roleGroup) {
        return ResponseData.ok(roleGroupService.update(roleGroup));
    }

    /**
     * 通过id删除角色组
     * @param id id
     * @return
     */
    @Override
    @ApiOperation(value = "通过id删除角色组", notes = "通过id删除角色组")
    public ResponseData<Integer> removeGroupById(Long id) {
        return ResponseData.ok(roleGroupService.deleteById(id));
    }

    /**
     * 查询角色组
     * @param id id
     * @return
     */
    @Override
    @ApiOperation(value = "查询商品角色组", notes = "查询商品角色组")
    public ResponseData<RoleGroup> getGroupById(Long id) {
        return ResponseData.ok(roleGroupService.getById(id));
    }

}
