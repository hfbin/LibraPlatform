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

package cn.hfbin.ucpm.service;


import cn.hfbin.ucpm.entity.IdentityEmployee;
import cn.hfbin.ucpm.params.EmployeeParams;
import cn.hfbin.ucpm.params.EmployeeQueryParams;
import cn.hfbin.ucpm.vo.IdentityInfoVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Author: huangfubin
 * @Description: 员工身份信息表
 * @Date: 2021-06-16
 */
public interface IdentityEmployeeService {
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param employeeQueryParams
     * @return
     */
    Page<IdentityInfoVo> page(Integer pageNo, Integer pageSize, EmployeeQueryParams employeeQueryParams);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    IdentityEmployee getById(Long id);


    /**
     * 更新数据
     * @param identityEmployee
     * @return
     */
    int updateSomeColumnById(IdentityEmployee identityEmployee);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     *
     * @description 根据accountId 或者mobile 查询用户信息
     * @param employee
     * @author huangfubin
     * @date 2021/7/21
     * @return BaseIdentityEmployee
     */
    IdentityEmployee select(IdentityEmployee employee);
    /**
     * 保存员工信息（如果不存在Account账号，则自动创建账号）
     * @param employeeParams
     * @return
     */
    Integer saveEmployee(EmployeeParams employeeParams);

    /**
     * 修改员工信息
     * @param employeeParams
     * @return
     */
    Integer updateEmployee(EmployeeParams employeeParams);

    /**
     * [初始化租户使用] 新增员工身份
     * @param employee
     * @return
     */
    int saveIgnoreTr(IdentityEmployee employee);
}
