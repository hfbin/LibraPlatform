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

import cn.hfbin.ucpm.params.InitTenantParams;
import cn.hfbin.ucpm.vo.InitTenantVo;
import cn.hfbin.common.core.api.ResponseData;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: huangfubin
 * @Description: 初始化租户相关信息的接口
 * @Date: 2021-06-16
 */
public interface InitTenantApiService {

    /**
     * 新增账户表
     * @param params 账户表
     * @return ResponseData
     */
     @PostMapping("/init")
     ResponseData<InitTenantVo> init(@RequestBody InitTenantParams params);


    /**
     * 同步租户默认管理员角色权限
     * @param params 账户表
     * @return ResponseData
     */
    @PostMapping("/sync-admin-role-menu-permission")
    ResponseData<Integer> syncAdminRoleMenuPermission(@RequestBody InitTenantParams params);

}
