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


import cn.hfbin.ucpm.entity.Menu;
import cn.hfbin.ucpm.entity.MenuInterfaceRef;
import cn.hfbin.ucpm.enums.UcPmExceptionCode;
import cn.hfbin.ucpm.enums.MenuTypeEnum;
import cn.hfbin.ucpm.mapper.MenuInterfaceRefMapper;
import cn.hfbin.ucpm.mapper.MenuMapper;
import cn.hfbin.ucpm.params.MenuInterfaceParams;
import cn.hfbin.ucpm.params.MenuParams;
import cn.hfbin.ucpm.service.MenuService;
import cn.hfbin.ucpm.vo.MenuInterfaceVo;
import cn.hfbin.ucpm.vo.TreeVo;
import cn.hfbin.ucpm.vo.RouterVo;
import cn.hfbin.common.core.context.SpringContextUtils;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.common.core.utils.FeignResponseUtil;
import cn.hfbin.tenant.client.TrTenantServiceClient;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: huangfubin
 * @Description: 菜单表
 * @Date: 2021-06-25
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuInterfaceRefMapper menuInterfaceRefMapper;

    @Autowired
    private TrTenantServiceClient trTenantServiceClient;

    /**
     * 分页查询
     *
     * @param pageNo
     * @param pageSize
     * @param menu
     * @return
     */
    @Override
    public Page<Menu> page(Integer pageNo, Integer pageSize, Menu menu) {
        Page<Menu> page = new Page<>(pageNo, pageSize);
        return baseMapper.selectPage(page, Wrappers.lambdaQuery(menu));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Menu getById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        return baseMapper.deleteById(id);
    }

    /**
     * 查询菜单
     *
     * @return
     */
    @Override
    public List<RouterVo> queryUserMenu() {
        List<Menu> list = baseMapper.selectList(Wrappers.<Menu>lambdaQuery()
                .ne(Menu::getMenuType, 2)
                .orderByAsc(Menu::getSortNo));
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return RouterVo.listToTree(list);
    }

    /**
     * 查询所有菜单
     *
     * @param menu
     * @return
     */
    @Override
    public List<Menu> list(Menu menu) {
        LambdaQueryWrapper<Menu> wrapper = Wrappers.lambdaQuery();
        if (!StrUtil.isBlank(menu.getName())) {
            wrapper.like(Menu::getName, menu.getName());
        }
        if (!StrUtil.isBlank(menu.getClientCode())) {
            wrapper.like(Menu::getClientCode, menu.getClientCode());
        }
        wrapper.orderByAsc(Menu::getSortNo);
        return baseMapper.selectList(wrapper);
    }

    /**
     * 查询菜单树结构
     *
     * @param menuParams
     * @return
     */
    @Override
    public List<TreeVo> tree(MenuParams menuParams) {
        // 查询租户已经开通的菜单权限
        List<Long> menuIds = FeignResponseUtil.get(trTenantServiceClient.selectMenu(SpringContextUtils.getTenantCode()));
        if(CollectionUtil.isEmpty(menuIds)){
            return null;
        }
        List<Menu> dbList = baseMapper.selectList(Wrappers.<Menu>lambdaQuery().orderByAsc(Menu::getSortNo));
        if (CollectionUtil.isEmpty(dbList)) {
            return null;
        }
        // 过滤不在租户内的菜单
        List<Menu> list = dbList.stream().filter(o -> menuIds.contains(o.getId())).collect(Collectors.toList());
        return getTreeVos(list);
    }

    /**
     * 查询菜单接口关联数据
     *
     * @param id
     * @return
     */
    @Override
    public MenuInterfaceVo interfaceRefById(Long id) {
        List<MenuInterfaceRef> list = menuInterfaceRefMapper.selectList(Wrappers.<MenuInterfaceRef>lambdaQuery().eq(MenuInterfaceRef::getMenuId, id));
        MenuInterfaceVo menuInterfaceVo = new MenuInterfaceVo();
        menuInterfaceVo.setIds(list.stream().map(MenuInterfaceRef::getInterfaceId).collect(Collectors.toList()));
        menuInterfaceVo.setId(id);
        return menuInterfaceVo;
    }

    /**
     * 保存菜单接口关联数据
     *
     * @param params
     * @return
     */
    @Override
    public Integer interfaceRefSave(MenuInterfaceParams params) {
        Menu menu = baseMapper.selectById(params.getId());
        Optional.ofNullable(menu).orElseThrow(() -> new LibraException(UcPmExceptionCode.MENU_NULL));
        menuInterfaceRefMapper.delete(Wrappers.<MenuInterfaceRef>lambdaQuery().eq(MenuInterfaceRef::getMenuId, params.getId()));
        List<MenuInterfaceRef> list = new ArrayList<>();
        params.getIds().forEach(data -> {
            MenuInterfaceRef menuInterfaceRef = new MenuInterfaceRef();
            menuInterfaceRef.setMenuId(menu.getId());
            menuInterfaceRef.setInterfaceId(data);
            menuInterfaceRef.setDelFlag(0);
            list.add(menuInterfaceRef);
        });
        return menuInterfaceRefMapper.insertBatchSomeColumn(list);
    }

    /**
     * 新增数据
     *
     * @param menu
     * @return
     */
    @Override
    public int insert(Menu menu) {
        setFields(menu);
        return baseMapper.insert(menu);
    }

    /**
     *
     * @description 新增修改公共参数设置
     * @param menu
     * @author huangfubin
     * @date 2021/8/29
     * @return void
     */
    private void setFields(Menu menu) {
        if(menu.getParentId() != 0L){
            Menu menuParent = baseMapper.selectById(menu.getParentId());
            Optional.ofNullable(menuParent).orElseThrow(() -> new LibraException(UcPmExceptionCode.SELECT_PARENT_ID_ERROR));
            menu.setClientCode(menuParent.getClientCode());
        }
        if (menu.getMenuType() == MenuTypeEnum.MASTER_MENU.getCode()) {
            menu.setParentId(0L);
            menu.setIsLeaf(0);
        } else {
            menu.setIsLeaf(1);
        }
    }

    /**
     * 更新数据(为null的字段不会更新)
     *
     * @param menu
     * @return
     */
    @Override
    public int update(Menu menu) {
        setFields(menu);
        return baseMapper.updateById(menu);
    }

    /**
     * 更新数据(为null的字段会更新)
     *
     * @param menu
     * @return
     */
    @Override
    public int updateSomeColumnById(Menu menu) {
        return baseMapper.alwaysUpdateSomeColumnById(menu);
    }



    /**
     * [平台使用]查询菜单树结构
     * @param menuParams
     * @return
     */
    @Override
    public List<TreeVo> treeIgnoreTr(MenuParams menuParams) {
        List<Menu> list = baseMapper.selectList(Wrappers.<Menu>lambdaQuery().orderByAsc(Menu::getSortNo));
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return getTreeVos(list);
    }

    /**
     * 树结构转换
     * @param list
     * @return
     */
    private List<TreeVo> getTreeVos(List<Menu> list) {
        List<TreeVo> treeVoList = new ArrayList<>();
        list.forEach(data -> {
            TreeVo treeVo = new TreeVo();
            treeVo.setParentId(data.getParentId());
            treeVo.setId(data.getId());
            treeVo.setName(data.getName());
            treeVo.setChild(new ArrayList<>());
            treeVoList.add(treeVo);
        });
        return TreeVo.listToTree(treeVoList);
    }
}
