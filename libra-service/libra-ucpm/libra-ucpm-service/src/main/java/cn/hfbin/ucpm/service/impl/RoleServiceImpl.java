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

import cn.hfbin.ucpm.entity.RelationRole;
import cn.hfbin.ucpm.entity.Role;
import cn.hfbin.ucpm.entity.RoleGroup;
import cn.hfbin.ucpm.entity.RoleMenuRef;
import cn.hfbin.ucpm.enums.RelationTypeEnum;
import cn.hfbin.ucpm.enums.UcPmExceptionCode;
import cn.hfbin.ucpm.mapper.RoleGroupMapper;
import cn.hfbin.ucpm.mapper.RoleMapper;
import cn.hfbin.ucpm.mapper.RoleMenuRefMapper;
import cn.hfbin.ucpm.params.RoleMenuParams;
import cn.hfbin.ucpm.params.RoleParams;
import cn.hfbin.ucpm.service.RelationRoleService;
import cn.hfbin.ucpm.service.RoleService;
import cn.hfbin.ucpm.vo.RelationRoleVo;
import cn.hfbin.ucpm.vo.RoleGroupVo;
import cn.hfbin.ucpm.vo.RoleMenuVo;
import cn.hfbin.ucpm.vo.RoleVo;
import cn.hfbin.common.core.context.SpringContextUtils;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.common.core.utils.DeptCodeUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: huangfubin
 * @Description: ?????????
 * @Date: 2021-06-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Autowired
    private RoleGroupMapper roleGroupMapper;

    @Autowired
    private RoleMenuRefMapper roleMenuRefMapper;

    @Autowired
    private RelationRoleService relationRoleService;
    /**
     * ????????????
     * @param pageNo
     * @param pageSize
     * @param role
     * @return
     */
    @Override
    public Page<Role> page(Integer pageNo, Integer pageSize, Role role) {
        Page<Role> page = new Page(pageNo, pageSize);
        return baseMapper.selectPage(page, Wrappers.lambdaQuery(role));
    }
    /**
     * ??????id??????
     * @param id
     * @return
     */
    @Override
    public Role getById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * ????????????
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        Role role = baseMapper.selectById(id);
        Optional.ofNullable(role).orElseThrow(()-> new LibraException(UcPmExceptionCode.ROLE_NULL));
        // ????????????????????????
        if(role.getSystemDefault() == 1) {
            throw new LibraException(UcPmExceptionCode.IDENTITY_EMPLOYEE_ADMIN);
        }
        // ?????????????????????????????????
        List<RelationRole> list = relationRoleService.selectListByRoleId(id);
        if(CollectionUtil.isNotEmpty(list)){
            throw new LibraException(UcPmExceptionCode.ROLE_BIND);
        }
        // ??????????????????
        LambdaQueryWrapper<RoleMenuRef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenuRef::getRoleId, id);
        roleMenuRefMapper.delete(queryWrapper);
        return baseMapper.deleteById(id);
    }

    /**
     * ??????????????????
     * @param roleParams
     * @return
     */
    @Override
    public List<RoleGroupVo> tree(RoleParams roleParams) {
        List<RoleGroup> roleGroups = roleGroupMapper.selectList(null);
        if(CollectionUtil.isEmpty(roleGroups)){
            return null;
        }
        List<Long> listIds = roleGroups.stream().map(RoleGroup::getId).collect(Collectors.toList());
        List<Role> roleList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(listIds)){
            LambdaQueryWrapper<Role> baseRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            baseRoleLambdaQueryWrapper.in(Role::getGroupId, listIds);
            roleList = baseMapper.selectList(baseRoleLambdaQueryWrapper);
        }
        List<RoleGroupVo> roleGroupVoList = new ArrayList<>();
        List<Role> finalRoleList = roleList;
        roleGroups.forEach(data -> {
            RoleGroupVo roleGroupVo = new RoleGroupVo();
            roleGroupVo.setId(data.getId());
            roleGroupVo.setName(data.getName());
            roleGroupVo.setRoleGroup(true);
            if(CollectionUtil.isNotEmpty(finalRoleList)) {
                List<Role> collect = finalRoleList.parallelStream().filter(o -> o.getGroupId().equals(data.getId())).collect(Collectors.toList());
                List<RoleGroupVo> roleVoList = new ArrayList<>();
                if (CollectionUtil.isNotEmpty(collect)) {
                    collect.forEach(roleData -> {
                        RoleGroupVo roleVo = new RoleGroupVo();
                        roleVo.setId(roleData.getId());
                        roleVo.setName(roleData.getRoleName());
                        roleVo.setRoleGroup(false);
                        roleVoList.add(roleVo);
                    });
                }
                roleGroupVo.setChildren(roleVoList);
            }
            roleGroupVoList.add(roleGroupVo);
        });
        return roleGroupVoList;
    }

    /**
     * ????????????????????????
     * @param roleMenuParams ????????????
     * @return Integer
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveRoleMenu(RoleMenuParams roleMenuParams) {
        Role role = this.getById(roleMenuParams.getRoleId());
        // ????????????????????????
        Optional.ofNullable(role).orElseThrow(() -> new LibraException(UcPmExceptionCode.ROLE_NULL));
        // ????????????????????????
        if(role.getSystemDefault() == 1) {
            throw new LibraException(UcPmExceptionCode.SYSTEM_DEFAULT_UPDATE);
        }
        LambdaQueryWrapper<RoleMenuRef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenuRef::getRoleId, role.getId());
        // ??????????????????
        roleMenuRefMapper.delete(queryWrapper);
        roleMenuParams.setTenantCode(SpringContextUtils.getTenantCode());
        return this.saveRoleMenuRef(roleMenuParams);
    }

    /**
     * ????????????????????????????????????
     * @param roleMenuParams
     * @return
     */
    @Override
    public Integer saveRoleMenuRef(RoleMenuParams roleMenuParams) {
        List<RoleMenuRef> menuRefList = new ArrayList<>();
        roleMenuParams.getMenuIds().forEach(data -> {
            RoleMenuRef roleMenuRef = new RoleMenuRef();
            roleMenuRef.setRoleId(roleMenuParams.getRoleId());
            roleMenuRef.setMenuId(data);
            roleMenuRef.setTenantCode(roleMenuParams.getTenantCode());
            roleMenuRef.setDelFlag(0);
            menuRefList.add(roleMenuRef);
        });
        return roleMenuRefMapper.insertBatchSomeColumn(menuRefList);
    }

    /**
     * [??????????????????]???????????????????????????????????????????????????
     * @param roleMenuParams
     * @return
     */
    @Override
    public int deleteRoleMenuRefIgnoreTr(RoleMenuParams roleMenuParams) {
        if(Objects.isNull(roleMenuParams) || CollectionUtil.isEmpty(roleMenuParams.getTenantCodes())){
            return 0;
        }
        return roleMenuRefMapper.deleteRoleMenuRefIgnoreTr(roleMenuParams);
    }

    /**
     * [??????????????????]???????????????????????????????????????????????????
     * @param roleParams
     * @return
     */
    @Override
    public List<Role> selectListIgnoreTr(RoleParams roleParams) {
        if(Objects.isNull(roleParams) || CollectionUtil.isEmpty(roleParams.getTenantCodes())){
            return null;
        }
        return baseMapper.selectListIgnoreTr(roleParams);
    }

    /**
     * ????????????????????????
     * @param roleId ??????id
     * @return List<RoleMenuVo>
     */
    @Override
    public RoleMenuVo getMenu(Long roleId) {
        Role role = this.getById(roleId);
        // ????????????????????????
        Optional.ofNullable(role).orElseThrow(() -> new LibraException(UcPmExceptionCode.ROLE_NULL));
        LambdaQueryWrapper<RoleMenuRef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenuRef::getRoleId, role.getId());
        List<RoleMenuRef> menuRefList = roleMenuRefMapper.selectList(queryWrapper);

        List<Long> collect = menuRefList.stream().map(RoleMenuRef::getMenuId).collect(Collectors.toList());
        RoleMenuVo roleMenuVo = new RoleMenuVo();
        roleMenuVo.setMenuIds(collect);
        roleMenuVo.setRoleId(role.getId());
        roleMenuVo.setSystemDefault(role.getSystemDefault());
        return roleMenuVo;
    }

    /**
     *
     * @description ??????ids????????????
     * @param roleList
     * @author huangfubin
     * @date 2021/7/21
     * @return java.util.List<BaseRole>
     */
    @Override
    public List<Role> getByIds(List<Long> roleList) {
        if(CollectionUtil.isEmpty(roleList)){
            return null;
        }
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Role::getId, roleList);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * ??????????????????
     * @param roleParams
     * @return
     */
    @Override
    public List<RoleVo> list(RoleParams roleParams) {
        List<Role> roleList = baseMapper.selectList(null);
        List<RoleVo> roleVoList = new ArrayList<>();
        roleList.forEach(data -> {
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(data, roleVo);
            roleVoList.add(roleVo);
        });
        return roleVoList;
    }

    /**
     * ???????????????????????????????????????
     * @param roleParams
     * @return
     */
    @Override
    public List<Role> selectList(RoleParams roleParams) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if(CollectionUtil.isNotEmpty(roleParams.getIds())){
            queryWrapper.in(Role::getId, roleParams.getIds());
        }
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * ????????????
     * @param role
     * @return
     */
    @Override
    public int insert(Role role) {
        role.setRoleCode(DeptCodeUtil.genCode(null));
        return baseMapper.insert(role);
    }

    /**
     * ????????????(???null?????????????????????)
     * @param role
     * @return
     */
    @Override
    public int update(Role role) {
        return baseMapper.updateById(role);
    }

    /**
     * ????????????(???null??????????????????)
     * @param role
     * @return
     */
    @Override
    public int updateSomeColumnById(Role role) {
        return baseMapper.alwaysUpdateSomeColumnById(role);
    }
}
