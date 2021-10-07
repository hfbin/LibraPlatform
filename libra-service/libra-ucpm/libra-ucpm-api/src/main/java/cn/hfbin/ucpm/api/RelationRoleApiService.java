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

import cn.hfbin.ucpm.entity.RelationRole;
import cn.hfbin.common.core.api.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;



/**
 * @Author: huangfubin
 * @Description: 角色关联表 服务调用接口
 * @Date: 2021-06-16
 */
public interface RelationRoleApiService {

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param relationRole 角色关联表
     * @return ResponseData
     */
    @GetMapping("/page")
    ResponseData<Page<RelationRole>> page(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                                          @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
                                          RelationRole relationRole);

    /**
     * 通过id查询角色关联表
     * @param id id
     * @return ResponseData
     */
    @GetMapping("/{id}")
    ResponseData<RelationRole> getById(@PathVariable("id" ) Long id);

    /**
     * 新增角色关联表
     * @param relationRole 角色关联表
     * @return ResponseData
     */
    @PostMapping("/save")
     ResponseData<Integer> save(@RequestBody RelationRole relationRole);

    /**
     * 修改角色关联表
     * @param relationRole 角色关联表
     * @return ResponseData
     */
    @PutMapping("/edit")
    ResponseData<Integer> updateById(@RequestBody RelationRole relationRole);

    /**
     * 通过id删除角色关联表
     * @param id id
     * @return ResponseData
     */
    @DeleteMapping("/delete/{id}")
    ResponseData<Integer> removeById(@PathVariable("id") Long id);

}
