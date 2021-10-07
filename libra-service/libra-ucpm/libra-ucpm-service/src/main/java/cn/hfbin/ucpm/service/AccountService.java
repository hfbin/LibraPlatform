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

import cn.hfbin.ucpm.entity.Account;
import cn.hfbin.ucpm.entity.Menu;
import cn.hfbin.ucpm.params.AccountParams;
import cn.hfbin.ucpm.params.AccountIdentityQueryParams;
import cn.hfbin.ucpm.params.RelationRoleParams;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 账户表
 * @Date: 2021-06-16
 */
public interface AccountService {
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param account
     * @return
     */
    Page<Account> page(Integer pageNo, Integer pageSize, Account account);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Account getById(Long id);

    /**
     * 新增数据
     * @param account
     * @return
     */
    int insert(Account account);

    /**
     * 更新数据(为null的字段不会更新)
     * @param account
     * @return
     */
    int update(Account account);

    /**
     * 更新数据
     * @param account
     * @return
     */
    int updateSomeColumnById(Account account);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 查询账号，如果autoCreate为True，如果不存在讲自动创建账号
     * 账号、手机号作为组合唯一识别
     * @param accountParams
     * @return
     */
    Account selectOrSaveAccount(AccountParams accountParams);

    /**
     *
     * @description 更新账号手机号
     * @param accountParams
     * @author huangfubin
     * @date 2021/7/23
     * @return void
     */
    Account updateAccountMobile(AccountParams accountParams);

    /**
     * 根据手机号或者用户查询
     * @param accountParams
     * @return
     */
    Account select(AccountIdentityQueryParams accountParams);

    /**
     * 重置密码
     * @param id
     * @return
     */
    int resetPassword(Long id);

    /**
     *
     * @description
     * @param relationRoleParams
     * @author huangfubin
     * @date 2021/9/30
     * @return java.util.List<cn.hfbin.ucpm.entity.Menu>
     */
    List<Menu> selectUserMenu(RelationRoleParams relationRoleParams);
}
