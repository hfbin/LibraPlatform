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

import cn.hfbin.ucpm.entity.Version;
import cn.hfbin.common.core.api.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;



/**
 * @Author: huangfubin
 * @Description: 版本 服务调用接口
 * @Date: 2021-08-27
 */
public interface VersionApiService {

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param version 版本
     * @return ResponseData
     */
    @GetMapping("/page")
    ResponseData<Page<Version>> page(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                                     @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
                                     Version version);

    /**
     * 通过id查询版本
     * @param id id
     * @return ResponseData
     */
    @GetMapping("/{id}")
    ResponseData<Version> getById(@PathVariable("id" ) Long id);

    /**
     * 新增版本
     * @param version 版本
     * @return ResponseData
     */
    @PostMapping("/save")
     ResponseData<Integer> save(@RequestBody Version version);

    /**
     * 修改版本
     * @param version 版本
     * @return ResponseData
     */
    @PutMapping("/edit")
    ResponseData<Integer> updateById(@RequestBody Version version);

    /**
     * 通过id删除版本
     * @param id id
     * @return ResponseData
     */
    @DeleteMapping("/delete/{id}")
    ResponseData<Integer> removeById(@PathVariable("id") Long id);

}
