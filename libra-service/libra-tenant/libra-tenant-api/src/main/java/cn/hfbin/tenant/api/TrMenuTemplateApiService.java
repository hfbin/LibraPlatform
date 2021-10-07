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

import cn.hfbin.tenant.entity.TrMenuTemplate;
import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.tenant.params.TrMenuTemplateParams;
import cn.hfbin.tenant.vo.TrMenuTemplateVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: huangfubin
 * @Description: 租户菜单模版表 服务调用接口
 * @Date: 2021-08-17
 */
public interface TrMenuTemplateApiService{

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param trMenuTemplate 租户菜单模版表
     * @return ResponseData
     */
    @GetMapping("/page")
    ResponseData<Page<TrMenuTemplate>> page(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                                     @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
                                     TrMenuTemplate trMenuTemplate);


    /**
     * 分页查询
     * @param trMenuTemplate 租户菜单模版表
     * @return ResponseData
     */
    @GetMapping("/list")
    ResponseData<List<TrMenuTemplate>> list(TrMenuTemplate trMenuTemplate);

    /**
     * 通过id查询租户菜单模版表
     * @param id id
     * @return ResponseData
     */
    @GetMapping("/{id}")
    ResponseData<TrMenuTemplateVo> getById(@PathVariable("id" ) Long id);

    /**
     * 新增租户菜单模版表
     * @param trMenuTemplate 租户菜单模版表
     * @return ResponseData
     */
    @PostMapping("/save")
     ResponseData<Integer> save(@RequestBody TrMenuTemplateParams trMenuTemplate);

    /**
     * 修改租户菜单模版表
     * @param trMenuTemplate 租户菜单模版表
     * @return ResponseData
     */
    @PutMapping("/edit")
    ResponseData<Integer> updateById(@RequestBody TrMenuTemplateParams trMenuTemplate);

    /**
     * 通过id删除租户菜单模版表
     * @param id id
     * @return ResponseData
     */
    @DeleteMapping("/delete/{id}")
    ResponseData<Integer> removeById(@PathVariable("id") Long id);

}
