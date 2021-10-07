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

package cn.hfbin.ucpm.controller;


import cn.hfbin.common.log.annotation.Log;
import cn.hfbin.common.log.enums.LogTypeEnum;
import cn.hfbin.common.log.enums.OptBehaviorEnum;
import cn.hfbin.ucpm.api.IdentityEmployeeApiService;
import cn.hfbin.ucpm.entity.IdentityEmployee;
import cn.hfbin.ucpm.params.EmployeeParams;
import cn.hfbin.ucpm.params.EmployeeQueryParams;
import cn.hfbin.ucpm.service.IdentityEmployeeService;
import cn.hfbin.ucpm.vo.IdentityInfoVo;
import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.common.security.annotation.PreAuthorize;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: huangfubin
 * @Description: 员工身份信息表
 * @Date: 2021-06-16
 */
@RestController
@RequestMapping("/identity/employee" )
@Api(tags = "员工身份信息管理")
public class IdentityEmployeeController implements IdentityEmployeeApiService {

    @Autowired
    private IdentityEmployeeService identityEmployeeService;

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param employeeQueryParams 员工身份信息表
     * @return ResponseData
     */
    @Override
    @PreAuthorize("emp:list")
    @ApiOperation(value = "分页查询数据", notes = "分页查询数据")
    @Log(desc = "员工管理-分页查询", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.SELECT)
    public ResponseData<Page<IdentityInfoVo>> page(Integer pageNo,
                                                   Integer pageSize,
                                                   EmployeeQueryParams employeeQueryParams) {
        return ResponseData.ok(identityEmployeeService.page(pageNo, pageSize, employeeQueryParams));
    }


    /**
     * 通过id查询员工身份信息表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @Log(desc = "员工管理-id查询", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.SELECT)
    public ResponseData<IdentityEmployee> getById(Long id) {
        return ResponseData.ok(identityEmployeeService.getById(id));
    }

    /**
     * 新增员工身份信息表
     * @param EmployeeParams 员工身份信息表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "新增员工身份信息表", notes = "新增员工身份信息表")
    @Log(desc = "员工管理-新增", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.ADD)
    public ResponseData<Integer> save(EmployeeParams EmployeeParams) {
        return ResponseData.ok(identityEmployeeService.saveEmployee(EmployeeParams));
    }

    /**
     * 修改员工身份信息表
     * @param employeeParams 员工身份信息表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "更新员工身份信息表", notes = "更新员工身份信息表")
    @Log(desc = "员工管理-更新", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.UPDATE)
    public ResponseData<Integer> updateById(EmployeeParams employeeParams) {
        return ResponseData.ok(identityEmployeeService.updateEmployee(employeeParams));
    }

    /**
     * 通过id删除员工身份信息表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id删除员工身份信息表", notes = "通过id删除员工身份信息表")
    @Log(desc = "员工管理-删除", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.DELETE)
    public ResponseData<Integer> removeById(Long id) {
        return ResponseData.ok(identityEmployeeService.deleteById(id));
    }

}
