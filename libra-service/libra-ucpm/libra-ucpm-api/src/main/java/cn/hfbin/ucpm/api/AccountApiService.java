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

import cn.hfbin.ucpm.entity.Account;
import cn.hfbin.ucpm.params.AccountIdentityQueryParams;
import cn.hfbin.ucpm.vo.AccountVo;
import cn.hfbin.ucpm.vo.IdentityInfoVo;
import cn.hfbin.common.core.api.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;



/**
 * @Author: huangfubin
 * @Description: 账户表 服务调用接口
 * @Date: 2021-06-16
 */
public interface AccountApiService {

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param account 账户表
     * @return ResponseData
     */
    @GetMapping("/page")
    ResponseData<Page<Account>> page(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                                     @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
                                     Account account);

    /**
     * 通过id查询账户表
     * @param id id
     * @return ResponseData
     */
    @GetMapping("/{id}")
    ResponseData<Account> getById(@PathVariable("id") Long id);

    /**
     * 新增账户表
     * @param account 账户表
     * @return ResponseData
     */
    @PostMapping("/save")
     ResponseData<Integer> save(@RequestBody Account account);

    /**
     * 修改账户表
     * @param account 账户表
     * @return ResponseData
     */
    @PutMapping("/edit")
    ResponseData<Integer> updateById(@RequestBody Account account);

    /**
     * 通过id删除账户表
     * @param id id
     * @return ResponseData
     */
    @DeleteMapping("/delete/{id}")
    ResponseData<Integer> removeById(@PathVariable("id") Long id);

    /**
     * 查询用户账号身份信息
     * @param params params
     * @return ResponseData
     */
    @PostMapping("/select-account")
    ResponseData<AccountVo> selectAccount(@RequestBody AccountIdentityQueryParams params);


    /**
     * 查询用户账号身份信息
     * @param accountId 账号id accountId
     * @return ResponseData
     */
    @GetMapping("/select-identity-info/{accountId}")
    ResponseData<IdentityInfoVo> selectIdentityInfo(@PathVariable("accountId") Long accountId);
}
