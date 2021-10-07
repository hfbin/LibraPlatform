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

package cn.hfbin.ucpm.client;

import cn.hfbin.ucpm.api.InitTenantApiService;
import cn.hfbin.ucpm.constant.UcPmConstant;
import cn.hfbin.ucpm.params.InitTenantParams;
import cn.hfbin.ucpm.vo.InitTenantVo;
import cn.hfbin.common.core.api.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @Author: huangfubin
 * @Description: 账户表 服务调用接口及熔断处理
 * @Date: 2021-06-18
 */
@FeignClient(name = UcPmConstant.UCPM_SERVICE_NAME, path = UcPmConstant.UCPM_PATH + "/tenant")
public interface InitTenantServiceClient extends InitTenantApiService {
}
