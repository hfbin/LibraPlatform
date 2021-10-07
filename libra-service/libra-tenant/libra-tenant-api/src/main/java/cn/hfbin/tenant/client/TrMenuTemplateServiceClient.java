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

package cn.hfbin.tenant.client;

import cn.hfbin.tenant.api.TrMenuTemplateApiService;
import cn.hfbin.tenant.constant.TenantConstant;
import cn.hfbin.tenant.entity.TrMenuTemplate;
import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.tenant.params.TrMenuTemplateParams;
import cn.hfbin.tenant.vo.TrMenuTemplateVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 租户菜单模版表 服务调用接口及熔断处理
 * @Date: 2021-08-17
 */
@FeignClient(name = TenantConstant.TENANT_SERVICE_NAME , path = "/menu/template")
public interface TrMenuTemplateServiceClient extends TrMenuTemplateApiService {
}
