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

package cn.hfbin.tenant.controller;

import cn.hfbin.tenant.api.TrMenuTemplateApiService;
import cn.hfbin.tenant.entity.TrMenuTemplate;
import cn.hfbin.tenant.params.TrMenuTemplateParams;
import cn.hfbin.tenant.service.TrMenuTemplateService;
import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.tenant.vo.TrMenuTemplateVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 租户菜单模版表
 * @Date: 2021-08-17
 */
@RestController
@RequestMapping("/menu/template" )
@Api(tags = "租户菜单模版表管理")
public class TrMenuTemplateController implements TrMenuTemplateApiService{

    @Autowired
    private TrMenuTemplateService trMenuTemplateService;

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param trMenuTemplate 租户菜单模版表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "分页查询数据", notes = "分页查询数据")
    public ResponseData<Page<TrMenuTemplate>> page(Integer pageNo,
                                                 Integer pageSize,
                                                TrMenuTemplate trMenuTemplate) {
        return ResponseData.ok(trMenuTemplateService.page(pageNo, pageSize, trMenuTemplate));
    }

    /**
     * 分页查询
     * @param trMenuTemplate 租户菜单模版表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "[不分页]查询数据", notes = "[不分页]查询数据")
    public ResponseData<List<TrMenuTemplate>> list(TrMenuTemplate trMenuTemplate) {
        return ResponseData.ok(trMenuTemplateService.list(trMenuTemplate));
    }


    /**
     * 通过id查询租户菜单模版表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    public ResponseData<TrMenuTemplateVo> getById(Long id) {
        return ResponseData.ok(trMenuTemplateService.getById(id));
    }

    /**
     * 新增租户菜单模版表
     * @param trMenuTemplate 租户菜单模版表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "新增租户菜单模版表", notes = "新增租户菜单模版表")
    public ResponseData<Integer> save(TrMenuTemplateParams trMenuTemplate) {
        return ResponseData.ok(trMenuTemplateService.insert(trMenuTemplate));
    }

    /**
     * 修改租户菜单模版表
     * @param trMenuTemplate 租户菜单模版表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "更新租户菜单模版表", notes = "更新租户菜单模版表")
    public ResponseData<Integer> updateById(TrMenuTemplateParams trMenuTemplate) {
        return ResponseData.ok(trMenuTemplateService.update(trMenuTemplate));
    }

    /**
     * 通过id删除租户菜单模版表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id删除租户菜单模版表", notes = "通过id删除租户菜单模版表")
    public ResponseData<Integer> removeById(Long id) {
        return ResponseData.ok(trMenuTemplateService.deleteById(id));
    }


    /**
     * 同步租户菜单权限缓存
     * @param id id
     * @return ResponseData
     */
    @ApiOperation(value = "同步租户菜单权限缓存", notes = "同步租户菜单权限缓存")
    @GetMapping("/sync-tenant-menu-permission/{id}")
    public ResponseData<Integer> syncTenantMenuPermission(@PathVariable("id" )Long id) {
        return ResponseData.ok(trMenuTemplateService.syncTenantMenuPermission(id));
    }

}
