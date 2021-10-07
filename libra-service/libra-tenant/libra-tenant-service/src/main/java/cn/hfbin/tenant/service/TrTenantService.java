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

package cn.hfbin.tenant.service;

import cn.hfbin.tenant.entity.TrTenant;
import cn.hfbin.tenant.params.TrTenantParams;
import cn.hfbin.tenant.vo.TrTenantVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 多租户信息表
 * @Date: 2021-06-16
 */
public interface TrTenantService {
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param baseTenant
     * @return
     */
    Page<TrTenantVo> page(Integer pageNo, Integer pageSize, TrTenant baseTenant);

    /**
     * 菜单租户菜单
     * @param tenantCode
     * @return
     */
    List<Long> selectMenu(String tenantCode);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    TrTenant getById(Long id);

    /**
     * 新增数据
     * @param baseTenant
     * @return
     */
    int insert(TrTenantParams baseTenant);

    /**
     * 更新数据(为null的字段不会更新)
     * @param baseTenant
     * @return
     */
    int update(TrTenant baseTenant);

    /**
     * 更新数据
     * @param baseTenant
     * @return
     */
    int updateSomeColumnById(TrTenant baseTenant);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据条件查询租户
     * @param trTenantParams
     * @return
     */
    List<TrTenant> select(TrTenantParams trTenantParams);

    /**
     * 根据code查询租户信息
     * @param tenantCode
     * @return
     */
    TrTenant selectByCode(String tenantCode);
}
