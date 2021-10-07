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

import cn.hfbin.common.core.utils.AssertUtil;
import cn.hfbin.common.redis.util.RedisUtil;
import cn.hfbin.tenant.api.TrTenantApiService;
import cn.hfbin.tenant.constant.TenantRedisKeyConstant;
import cn.hfbin.tenant.entity.TrTenant;
import cn.hfbin.tenant.enums.TenantExceptionCode;
import cn.hfbin.tenant.params.TrTenantParams;
import cn.hfbin.tenant.service.TrTenantService;
import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.tenant.vo.TrTenantVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 多租户信息表
 * @Date: 2021-06-16
 */
@RestController
@RequestMapping("/tenant" )
@Api(tags = "多租户信息表管理")
public class TrTenantController implements TrTenantApiService {

    @Autowired
    private TrTenantService trTenantService;

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param baseTenant 多租户信息表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "分页查询数据", notes = "分页查询数据")
    public ResponseData<Page<TrTenantVo>> page(Integer pageNo,
                                               Integer pageSize,
                                               TrTenant baseTenant) {
        return ResponseData.ok(trTenantService.page(pageNo, pageSize, baseTenant));
    }


    /**
     * 查询租户菜单
     * @param tenantCode tenantCode
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "查询租户菜单ids", notes = "查询租户菜单ids")
    public ResponseData<List<Long>> selectMenu(String tenantCode) {
        return ResponseData.ok(trTenantService.selectMenu(tenantCode));
    }

    /**
     * 通过id查询多租户信息表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    public ResponseData<TrTenant> getById(Long id) {
        return ResponseData.ok(trTenantService.getById(id));
    }

    /**
     * 新增多租户信息表
     * @param baseTenant 多租户信息表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "新增多租户信息表", notes = "新增多租户信息表")
    public ResponseData<Integer> save(TrTenantParams baseTenant) {
        return ResponseData.ok(trTenantService.insert(baseTenant));
    }

    /**
     * 修改多租户信息表
     * @param tenantParams 多租户信息表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "更新多租户信息表", notes = "更新多租户信息表")
    public ResponseData<Integer> updateById(TrTenantParams tenantParams) {
        TrTenant trTenant = new TrTenant();
        BeanUtils.copyProperties(tenantParams,trTenant);
        return ResponseData.ok(trTenantService.update(trTenant));
    }

    /**
     * 通过id删除多租户信息表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id删除多租户信息表", notes = "通过id删除多租户信息表")
    public ResponseData<Integer> removeById(Long id) {
        return ResponseData.ok(trTenantService.deleteById(id));
    }

    /**
     * 根据code查询租户信息
     * @param tenantCode
     * @return
     */
    @Override
    public ResponseData<TrTenant> selectByCode(String tenantCode) {
        AssertUtil.notBlank(tenantCode, TenantExceptionCode.TENANT_CODE_IS_NULL);
        return ResponseData.ok(trTenantService.selectByCode(tenantCode));
    }

}
