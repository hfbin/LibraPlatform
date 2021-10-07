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

import cn.hfbin.ucpm.api.RelationRoleApiService;
import cn.hfbin.ucpm.entity.RelationRole;
import cn.hfbin.ucpm.service.RelationRoleService;
import cn.hfbin.common.core.api.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: huangfubin
 * @Description: 角色关联表
 * @Date: 2021-06-16
 */
@RestController
@RequestMapping("/relationrole" )
@Api(tags = "角色关联管理")
public class RelationRoleController implements RelationRoleApiService {

    @Autowired
    private RelationRoleService relationRoleService;

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param relationRole 角色关联表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "分页查询数据", notes = "分页查询数据")
    public ResponseData<Page<RelationRole>> page(Integer pageNo,
                                                 Integer pageSize,
                                                 RelationRole relationRole) {
        return ResponseData.ok(relationRoleService.page(pageNo, pageSize, relationRole));
    }


    /**
     * 通过id查询角色关联表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    public ResponseData<RelationRole> getById(Long id) {
        return ResponseData.ok(relationRoleService.getById(id));
    }

    /**
     * 新增角色关联表
     * @param relationRole 角色关联表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "新增角色关联表", notes = "新增角色关联表")
    public ResponseData<Integer> save(RelationRole relationRole) {
        return ResponseData.ok(relationRoleService.insert(relationRole));
    }

    /**
     * 修改角色关联表
     * @param relationRole 角色关联表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "更新角色关联表", notes = "更新角色关联表")
    public ResponseData<Integer> updateById(RelationRole relationRole) {
        return ResponseData.ok(relationRoleService.update(relationRole));
    }

    /**
     * 通过id删除角色关联表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id删除角色关联表", notes = "通过id删除角色关联表")
    public ResponseData<Integer> removeById(Long id) {
        return ResponseData.ok(relationRoleService.deleteById(id));
    }

}
