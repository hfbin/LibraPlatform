package cn.hfbin.ucpm.service;

import cn.hfbin.ucpm.params.AccountIdentityQueryParams;
import cn.hfbin.ucpm.params.TreeParams;
import cn.hfbin.ucpm.vo.AccountVo;
import cn.hfbin.ucpm.vo.IdentityInfoVo;
import cn.hfbin.ucpm.vo.PermissionResourceVo;

/**
 * @Author: huangfubin
 * @Description: AccountIdentityService 类
 * @Date: 2021/8/9
 */
public interface AccountIdentityService {

    /**
     * 查询用户账号身份信息
     * @param accountParams
     * @return
     */
    AccountVo selectAccount(AccountIdentityQueryParams accountParams);

    /**
     *
     * @description 查询用户菜单及按钮权限，如果有多身份的设计需另外扩展目前写死一个身份
     * @author huangfubin
     * @date 2021/8/8
     * @return
     */
    PermissionResourceVo selectUserMenu(TreeParams treeParams);

    /**
     * 根据id查询账号身份信息
     * @param id
     * @return
     */
    IdentityInfoVo selectById(Long id);
}
