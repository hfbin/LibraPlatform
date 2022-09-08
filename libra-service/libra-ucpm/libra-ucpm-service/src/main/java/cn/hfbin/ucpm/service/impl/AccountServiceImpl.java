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

package cn.hfbin.ucpm.service.impl;


import cn.hfbin.common.core.constant.SpecialCharacterPool;
import cn.hfbin.ucpm.entity.Account;
import cn.hfbin.ucpm.entity.Menu;
import cn.hfbin.ucpm.enums.AccountStatusEnum;
import cn.hfbin.ucpm.enums.UcPmExceptionCode;
import cn.hfbin.ucpm.mapper.AccountMapper;
import cn.hfbin.ucpm.params.AccountParams;
import cn.hfbin.ucpm.params.AccountIdentityQueryParams;
import cn.hfbin.ucpm.params.RelationRoleParams;
import cn.hfbin.ucpm.service.AccountService;
import cn.hfbin.common.core.exception.LibraException;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: huangfubin
 * @Description: 账户表
 * @Date: 2021-06-16
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param account
     * @return
     */
    @Override
    public Page<Account> page(Integer pageNo, Integer pageSize, Account account) {
        Page<Account> page = new Page(pageNo, pageSize);
        return baseMapper.selectPage(page, Wrappers.lambdaQuery(account));
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Account getById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        return baseMapper.deleteById(id);
    }

    /**
     * 查询账号，如果autoCreate为True，如果不存在讲自动创建账号
     * 账号、手机号作为组合唯一识别
     * @param accountParams
     * @return
     */
    @Override
    public Account selectOrSaveAccount(AccountParams accountParams) {
        String username = accountParams.getUsername();
        String mobile = accountParams.getMobile();
        Account account = null;
        LambdaQueryWrapper<Account> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Account::getUsername, username);
        queryWrapper.or().eq(Account::getMobile, accountParams.getMobile());
        List<Account> accounts = baseMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(accounts)){
            // 此写法避免查询分开两次用户名和表手机号
            List<Account> userNameList = accounts.stream().filter(o -> o.getUsername().equals(username)).collect(Collectors.toList());
            List<Account> mobileList = accounts.stream().filter(o -> o.getMobile().equals(mobile)).collect(Collectors.toList());
            if(CollectionUtil.isNotEmpty(userNameList)
                    && CollectionUtil.isNotEmpty(mobileList)
                    && userNameList.get(0).getUsername().equals(username)
                    && mobileList.get(0).getMobile().equals(mobile)){
                account = userNameList.get(0);
            }else {
                throw new LibraException(UcPmExceptionCode.ACCOUNT_TO_MANY);
            }
        }else {
            // 开启自动创建账号则自动创建账号
            if(accountParams.isAutoCreate()){
                account = new Account();
                account.setUsername(username);
                account.setMobile(mobile);
                account.setPassword(SecureUtil.md5(accountParams.getPassword()));
                account.setStatus(AccountStatusEnum.ENABLE.getCode());
                baseMapper.insert(account);
            }else {
                // 账号不存在
                throw new LibraException(UcPmExceptionCode.ACCOUNT_IS_NULL);
            }
        }
        return account;
    }

    /**
     *
     * @description 更新账号手机号
     * @param accountParams
     * @author huangfubin
     * @date 2021/7/23
     * @return BaseAccount
     */
    @Override
    public Account updateAccountMobile(AccountParams accountParams) {
        Account account = baseMapper.selectById(accountParams.getId());
        Optional.ofNullable(account).orElseThrow(() -> new LibraException(UcPmExceptionCode.ACCOUNT_IS_NULL));
        Account newAccount = new Account();
        newAccount.setId(accountParams.getId());
        newAccount.setPassword(SecureUtil.md5(accountParams.getPassword()));
        newAccount.setMobile(accountParams.getMobile());
        baseMapper.updateById(newAccount);
        return account;
    }

    /**
     * 根据手机号或者用户查询
     * @param accountParams
     * @return
     */
    @Override
    public Account select(AccountIdentityQueryParams accountParams) {
        LambdaQueryWrapper<Account> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(accountParams.getUsername())){
            queryWrapper.eq(Account::getUsername, accountParams.getUsername());
        }
        if(StrUtil.isNotBlank(accountParams.getMobile())){
            queryWrapper.eq(Account::getMobile, accountParams.getMobile());
        }
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public int resetPassword(Long id) {
        Account account = baseMapper.selectById(id);
        Optional.ofNullable(account).orElseThrow(() -> new LibraException(UcPmExceptionCode.ACCOUNT_IS_NULL));
        Account newAccount = new Account();
        newAccount.setId(account.getId());
        newAccount.setPassword(SecureUtil.md5(SpecialCharacterPool.DEF_PASSWORD));
        return baseMapper.updateById(newAccount);
    }

    @Override
    public List<Menu> selectUserMenu(RelationRoleParams relationRoleParams) {
        return baseMapper.selectUserMenu(relationRoleParams);
    }

    /**
     * 新增数据
     * @param account
     * @return
     */
    @Override
    public int insert(Account account) {
        account.setPassword(SecureUtil.md5(account.getPassword()));
        return baseMapper.insert(account);
    }

    /**
     * 更新数据(为null的字段不会更新)
     * @param account
     * @return
     */
    @Override
    public int update(Account account) {
        return baseMapper.updateById(account);
    }

    /**
     * 更新数据(为null的字段会更新)
     * @param account
     * @return
     */
    @Override
    public int updateSomeColumnById(Account account) {
        return baseMapper.alwaysUpdateSomeColumnById(account);
    }
}
