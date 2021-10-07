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


import cn.hfbin.ucpm.api.InitTenantApiService;
import cn.hfbin.ucpm.params.InitTenantParams;
import cn.hfbin.ucpm.service.InitTenantService;
import cn.hfbin.ucpm.vo.InitTenantVo;
import cn.hfbin.common.core.api.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: huangfubin
 * @Description: 账号表
 * @Date: 2021-06-16
 */
@RestController
@RequestMapping("/tenant")
@Api(tags = "账户管理")
public class InitTenantController implements InitTenantApiService {

    @Autowired
    InitTenantService tenantService;

    @Override
    @ApiOperation(value = "新增账户表", notes = "新增账户表")
    public ResponseData<InitTenantVo> init(InitTenantParams params) {
        return ResponseData.ok(tenantService.initTenant(params));
    }

    @Override
    public ResponseData<Integer> syncAdminRoleMenuPermission(InitTenantParams params) {
        return ResponseData.ok(tenantService.syncAdminRoleMenuPermission(params));
    }
}
