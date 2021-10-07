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

package cn.hfbin.tenant.api;

import cn.hfbin.tenant.entity.TrTenant;
import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.tenant.params.TrTenantParams;
import cn.hfbin.tenant.vo.TrTenantVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: huangfubin
 * @Description: 多租户信息表 服务调用接口
 * @Date: 2021-06-16
 */
public interface TrTenantApiService {

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param baseTenant 多租户信息表
     * @return ResponseData
     */
    @GetMapping("/page")
    ResponseData<Page<TrTenantVo>> page(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                                        @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
                                        TrTenant baseTenant);

    /**
     * 查询租户菜单ids
     * @param tenantCode
     * @return
     */
    @GetMapping("/select-menu")
    ResponseData<List<Long>> selectMenu(@RequestParam("tenantCode" ) String tenantCode);
    /**
     * 通过id查询多租户信息表
     * @param id id
     * @return ResponseData
     */
    @GetMapping("/{id}")
    ResponseData<TrTenant> getById(@PathVariable("id") Long id);

    /**
     * 新增多租户信息表
     * @param baseTenant 多租户信息表
     * @return ResponseData
     */
    @PostMapping("/save")
     ResponseData<Integer> save(@RequestBody TrTenantParams baseTenant);

    /**
     * 修改多租户信息表
     * @param baseTenant 多租户信息表
     * @return ResponseData
     */
    @PutMapping("/edit")
    ResponseData<Integer> updateById(@RequestBody TrTenantParams baseTenant);

    /**
     * 通过id删除多租户信息表
     * @param id id
     * @return ResponseData
     */
    @DeleteMapping("/delete/{id}")
    ResponseData<Integer> removeById(@PathVariable("id") Long id);

    /**
     * 根据code查询租户信息
     * @param tenantCode
     * @return
     */
    @GetMapping("/select-by-code")
    ResponseData<TrTenant> selectByCode(@RequestParam("tenantCode") String tenantCode);
}
