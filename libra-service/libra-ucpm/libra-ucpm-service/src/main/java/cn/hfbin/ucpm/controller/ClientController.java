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

import cn.hfbin.common.core.utils.AssertUtil;
import cn.hfbin.ucpm.api.ClientApiService;
import cn.hfbin.ucpm.entity.Client;
import cn.hfbin.ucpm.enums.UcPmExceptionCode;
import cn.hfbin.ucpm.service.ClientService;
import cn.hfbin.common.core.api.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 客户端管理
 * @Date: 2021-08-27
 */
@RestController
@RequestMapping("/client" )
@Api(tags = "客户端管理管理")
public class ClientController implements ClientApiService {

    @Autowired
    private ClientService clientService;

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param client 客户端管理
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "分页查询数据", notes = "分页查询数据")
    public ResponseData<Page<Client>> page(Integer pageNo,
                                           Integer pageSize,
                                           Client client) {
        return ResponseData.ok(clientService.page(pageNo, pageSize, client));
    }

    /**
     *  （不分页）查询列表
     * @description
     * @param
     * @author huangfubin
     * @date 2021/8/27
     * @return cn.hfbin.common.core.api.ResponseData<java.util.List<BaseClient>>
     */
    @Override
    @ApiOperation(value = "（不分页）查询列表", notes = "（不分页）查询列表")
    public ResponseData<List<Client>> list() {
        return ResponseData.ok(clientService.list(null));
    }


    /**
     * 通过id查询客户端管理
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    public ResponseData<Client> getById(Long id) {
        return ResponseData.ok(clientService.getById(id));
    }

    /**
     * 新增客户端管理
     * @param client 客户端管理
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "新增客户端管理", notes = "新增客户端管理")
    public ResponseData<Integer> save(Client client) {
        return ResponseData.ok(clientService.insert(client));
    }

    /**
     * 修改客户端管理
     * @param client 客户端管理
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "更新客户端管理", notes = "更新客户端管理")
    public ResponseData<Integer> updateById(Client client) {
        return ResponseData.ok(clientService.update(client));
    }

    /**
     * 通过id删除客户端管理
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id删除客户端管理", notes = "通过id删除客户端管理")
    public ResponseData<Integer> removeById(Long id) {
        return ResponseData.ok(clientService.deleteById(id));
    }

    /**
     * 根据客户端编码查询详细信息
     * @param clientCode
     * @return
     */
    @Override
    public ResponseData<Client> selectByCode(String clientCode) {
        AssertUtil.notBlank(clientCode, UcPmExceptionCode.CLIENT_CODE_IS_NULL);
        return ResponseData.ok(clientService.selectByCode(clientCode));
    }

}
