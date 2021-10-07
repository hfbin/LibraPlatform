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

import cn.hfbin.tenant.entity.TrMenuTemplate;
import cn.hfbin.tenant.params.TrMenuTemplateParams;
import cn.hfbin.tenant.vo.TrMenuTemplateVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 租户菜单模版表
 * @Date: 2021-08-17
 */
public interface TrMenuTemplateService{
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param trMenuTemplate
     * @return
     */
    Page<TrMenuTemplate> page(Integer pageNo, Integer pageSize, TrMenuTemplate trMenuTemplate);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    TrMenuTemplateVo getById(Long id);

    /**
     * 新增数据
     * @param trMenuTemplate
     * @return
     */
    int insert(TrMenuTemplateParams trMenuTemplate);

    /**
     * 更新数据(为null的字段不会更新)
     * @param trMenuTemplate
     * @return
     */
    int update(TrMenuTemplateParams trMenuTemplate);

    /**
     * 更新数据
     * @param trMenuTemplate
     * @return
     */
    int updateSomeColumnById(TrMenuTemplate trMenuTemplate);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 查询列表
     * @param trMenuTemplate
     * @return
     */
    List<TrMenuTemplate> list(TrMenuTemplate trMenuTemplate);

    /**
     * 同步租户菜单权限缓存
     * @param id
     * @return
     */
    int syncTenantMenuPermission(Long id);

    /**
     * 根据id查询绑定的菜单id
     * @param trMenuTemplate
     * @return
     */
    List<Long> selectMenuTemplateRef(TrMenuTemplate trMenuTemplate);
}
