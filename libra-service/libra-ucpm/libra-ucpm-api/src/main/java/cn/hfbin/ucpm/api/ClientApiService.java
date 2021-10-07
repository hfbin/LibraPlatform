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

import cn.hfbin.ucpm.entity.Client;
import cn.hfbin.common.core.api.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: huangfubin
 * @Description: 客户端管理 服务调用接口
 * @Date: 2021-08-27
 */
public interface ClientApiService {

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param client 客户端管理
     * @return ResponseData
     */
    @GetMapping("/page")
    ResponseData<Page<Client>> page(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                                    @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
                                    Client client);
    /**
     * （不分页）查询列表
     * @return ResponseData
     */
    @GetMapping("list")
    ResponseData<List<Client>> list();
    /**
     * 通过id查询客户端管理
     * @param id id
     * @return ResponseData
     */
    @GetMapping("/{id}")
    ResponseData<Client> getById(@PathVariable("id" ) Long id);

    /**
     * 新增客户端管理
     * @param client 客户端管理
     * @return ResponseData
     */
    @PostMapping("/save")
     ResponseData<Integer> save(@RequestBody Client client);

    /**
     * 修改客户端管理
     * @param client 客户端管理
     * @return ResponseData
     */
    @PutMapping("/update")
    ResponseData<Integer> updateById(@RequestBody Client client);

    /**
     * 通过id删除客户端管理
     * @param id id
     * @return ResponseData
     */
    @DeleteMapping("/delete/{id}")
    ResponseData<Integer> removeById(@PathVariable("id") Long id);

    /**
     * 根据客户端编码查询详细信息
     * @param clientCode
     * @return
     */
    @GetMapping("/select-by-code")
    ResponseData<Client> selectByCode(@RequestParam("clientCode")String clientCode);
}
