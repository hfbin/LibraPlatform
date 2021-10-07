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

import cn.hfbin.ucpm.entity.Depart;
import cn.hfbin.ucpm.params.DepartParams;
import cn.hfbin.ucpm.vo.DepartTreeVo;
import cn.hfbin.ucpm.vo.DepartVo;
import cn.hfbin.common.core.api.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: huangfubin
 * @Description: 组织机构表 服务调用接口
 * @Date: 2021-06-16
 */
public interface DepartApiService {

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param baseDepart 组织机构表
     * @return ResponseData
     */
    @GetMapping("/page")
    ResponseData<Page<Depart>> page(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                                    @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
                                    DepartParams baseDepart);

    /**
     * 部门列表（树结构）
     */
    @GetMapping("/tree")
    ResponseData<List<DepartTreeVo>> list(DepartParams departParams);

    /**
     * 查询一级部门
     */
    @GetMapping("/first-list")
    ResponseData<List<DepartVo>> firstTreeList(DepartParams departParams);


    /**
     * 通过id查询组织机构表
     * @param id id
     * @return ResponseData
     */
    @GetMapping("/{id}")
    ResponseData<DepartVo> getById(@PathVariable("id" ) Long id);

    /**
     * 新增组织机构表
     * @param depart 组织机构表
     * @return ResponseData
     */
    @PostMapping("/save")
     ResponseData<Integer> save(@RequestBody Depart depart);

    /**
     * 修改组织机构表
     * @param depart 组织机构表
     * @return ResponseData
     */
    @PutMapping("/edit")
    ResponseData<Integer> updateById(@RequestBody Depart depart);

    /**
     * 通过id删除组织机构表
     * @param id id
     * @return ResponseData
     */
    @DeleteMapping("/delete/{id}")
    ResponseData<Integer> removeById(@PathVariable("id") Long id);

}
