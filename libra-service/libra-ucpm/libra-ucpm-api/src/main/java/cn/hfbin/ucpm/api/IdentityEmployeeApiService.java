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


import cn.hfbin.ucpm.entity.IdentityEmployee;
import cn.hfbin.ucpm.params.EmployeeParams;
import cn.hfbin.ucpm.params.EmployeeQueryParams;
import cn.hfbin.ucpm.vo.IdentityInfoVo;
import cn.hfbin.common.core.api.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;



/**
 * @Author: huangfubin
 * @Description: 员工身份信息表 服务调用接口
 * @Date: 2021-06-16
 */
public interface IdentityEmployeeApiService {

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param employeeQueryParams 员工身份信息表
     * @return ResponseData
     */
    @GetMapping("/page")
    ResponseData<Page<IdentityInfoVo>> page(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                                            @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
                                            EmployeeQueryParams employeeQueryParams);

    /**
     * 通过id查询员工身份信息表
     * @param id id
     * @return ResponseData
     */
    @GetMapping("/{id}")
    ResponseData<IdentityEmployee> getById(@PathVariable("id" ) Long id);

    /**
     * 新增员工身份信息表
     * @param employeeParams 员工身份信息表
     * @return ResponseData
     */
    @PostMapping("/save")
     ResponseData<Integer> save(@RequestBody EmployeeParams employeeParams);

    /**
     * 修改员工身份信息表
     * @param baseIdentityEmployee 员工身份信息表
     * @return ResponseData
     */
    @PutMapping("/edit")
    ResponseData<Integer> updateById(@RequestBody EmployeeParams baseIdentityEmployee);

    /**
     * 通过id删除员工身份信息表
     * @param id id
     * @return ResponseData
     */
    @DeleteMapping("/delete/{id}")
    ResponseData<Integer> removeById(@PathVariable("id") Long id);

}
