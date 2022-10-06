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

import cn.hfbin.ucpm.api.AccountApiService;
import cn.hfbin.ucpm.entity.Account;
import cn.hfbin.ucpm.params.AccountIdentityQueryParams;
import cn.hfbin.ucpm.service.AccountIdentityService;
import cn.hfbin.ucpm.service.AccountService;
import cn.hfbin.ucpm.vo.AccountVo;
import cn.hfbin.ucpm.vo.IdentityInfoVo;
import cn.hfbin.common.core.api.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: huangfubin
 * @Description: 账号表
 * @Date: 2021-06-16
 */
@RestController
@RequestMapping("/account" )
@Api(tags = "账户管理")
public class AccountController implements AccountApiService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountIdentityService accountIdentityService;

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param account 账户表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "分页查询数据", notes = "分页查询数据")
    public ResponseData<Page<Account>> page(Integer pageNo,
                                            Integer pageSize,
                                            Account account) {
        return ResponseData.ok(accountService.page(pageNo, pageSize, account));
    }


    /**
     * 通过id查询账户表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    public ResponseData<Account> getById(Long id) {
        return ResponseData.ok(accountService.getById(id));
    }

    /**
     * 新增账户表
     * @param account 账户表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "新增账户表", notes = "新增账户表")
    public ResponseData<Integer> save(Account account) {
        return ResponseData.ok(accountService.insert(account));
    }

    /**
     * 修改账户表
     * @param account 账户表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "更新账户表", notes = "更新账户表")
    public ResponseData<Integer> updateById(Account account) {
        return ResponseData.ok(accountService.update(account));
    }
    /**
     * 通过id删除账户表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id删除账户表", notes = "通过id删除账户表")
    public ResponseData<Integer> removeById(Long id) {
        return ResponseData.ok(accountService.deleteById(id));
    }

    /**
     *
     * @description 查询用户账号身份信息
     * @param params
     * @author huangfubin
     * @date 2021/8/8
     * @return cn.hfbin.common.core.api.ResponseData<AccountIdentityVo>
     */
    @Override
    @ApiOperation(value = "查询用户账号身份信息", notes = "查询用户账号身份信息")
    public ResponseData<AccountVo> selectAccount(AccountIdentityQueryParams params) {
        // TODO:校验 2021/8/9
        return ResponseData.ok(accountIdentityService.selectAccount(params));
    }

    /**
     *
     * @description 查询用户账号身份信息
     * @author huangfubin
     * @date 2021/8/8
     * @return cn.hfbin.common.core.api.ResponseData<IdentityInfoVo>
     */
    @Override
    @ApiOperation(value = "查询用户账号身份信息", notes = "查询用户账号身份信息")
    public ResponseData<IdentityInfoVo> selectIdentityInfo(Long accountId) {
        return ResponseData.ok(accountIdentityService.selectById(accountId));
    }

    /**
     *
     * @description 重置密码
     * @author huangfubin
     * @date 2021/8/8
     * @return cn.hfbin.common.core.api.ResponseData<IdentityInfoVo>
     */
    @ApiOperation(value = "用户账号密码重置", notes = "用户账号密码重置")
    @PutMapping("/{id}")
    public ResponseData<Integer> resetPassword(@PathVariable("id") Long id) {
        return ResponseData.ok(accountService.resetPassword(id));
    }

}
