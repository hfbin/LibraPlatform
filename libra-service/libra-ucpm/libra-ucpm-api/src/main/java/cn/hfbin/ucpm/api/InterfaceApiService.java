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

import cn.hfbin.ucpm.entity.Interface;
import cn.hfbin.ucpm.params.MenuParams;
import cn.hfbin.ucpm.vo.TreeVo;
import cn.hfbin.common.core.api.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: huangfubin
 * @Description: 接口表 服务调用接口
 * @Date: 2021-06-18
 */
public interface InterfaceApiService {

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param anInterface 接口表
     * @return ResponseData
     */
    @GetMapping("/page")
    ResponseData<Page<Interface>> page(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                                       @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
                                       Interface anInterface);

    /**
     * 分页查询
     * @param menuParams 接口表
     * @return ResponseData
     */
    @GetMapping("/tree")
    ResponseData<List<TreeVo>> page(MenuParams menuParams);

    /**
     * 查询所有接口数据
     * @param anInterface 接口表
     * @return ResponseData
     */
    @GetMapping("/list")
    ResponseData<List<Interface>> listAll(Interface anInterface);

    /**
     * 通过id查询接口表
     * @param id id
     * @return ResponseData
     */
    @GetMapping("/{id}")
    ResponseData<Interface> getById(@PathVariable("id" ) Long id);

    /**
     * 新增接口表
     * @param anInterface 接口表
     * @return ResponseData
     */
    @PostMapping("/save")
     ResponseData<Integer> save(@RequestBody Interface anInterface);

    /**
     * 修改接口表
     * @param anInterface 接口表
     * @return ResponseData
     */
    @PutMapping("/edit")
    ResponseData<Integer> updateById(@RequestBody Interface anInterface);

    /**
     * 通过id删除接口表
     * @param id id
     * @return ResponseData
     */
    @DeleteMapping("delete/{id}")
    ResponseData<Integer> removeById(@PathVariable("id") Long id);

}
