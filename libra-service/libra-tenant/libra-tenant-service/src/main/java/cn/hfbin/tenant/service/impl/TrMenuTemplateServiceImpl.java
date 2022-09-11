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

package cn.hfbin.tenant.service.impl;


import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.common.core.utils.FeignResponseUtil;
import cn.hfbin.common.redis.util.RedisUtil;
import cn.hfbin.tenant.constant.TenantRedisKeyConstant;
import cn.hfbin.tenant.entity.TrMenuTemplate;
import cn.hfbin.tenant.entity.TrMenuTemplateRef;
import cn.hfbin.tenant.entity.TrTenant;
import cn.hfbin.tenant.enums.TenantExceptionCode;
import cn.hfbin.tenant.mapper.TrMenuTemplateMapper;
import cn.hfbin.tenant.mapper.TrMenuTemplateRefMapper;
import cn.hfbin.tenant.params.TrMenuTemplateParams;
import cn.hfbin.tenant.params.TrTenantParams;
import cn.hfbin.tenant.service.TrMenuTemplateService;
import cn.hfbin.tenant.service.TrTenantService;
import cn.hfbin.tenant.vo.TrMenuTemplateVo;
import cn.hfbin.ucpm.client.InitTenantServiceClient;
import cn.hfbin.ucpm.params.InitTenantParams;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: huangfubin
 * @Description: 租户菜单模版表
 * @Date: 2021-08-17
 */
@Slf4j
@Service
public class TrMenuTemplateServiceImpl extends ServiceImpl<TrMenuTemplateMapper, TrMenuTemplate> implements TrMenuTemplateService {

    @Autowired
    private InitTenantServiceClient baseInitTenantServiceClient;
    @Autowired
    private TrMenuTemplateRefMapper trMenuTemplateRefMapper;
    @Autowired
    private TrTenantService trTenantService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 分页查询
     *
     * @param pageNo
     * @param pageSize
     * @param trMenuTemplate
     * @return
     */
    @Override
    public Page<TrMenuTemplate> page(Integer pageNo, Integer pageSize, TrMenuTemplate trMenuTemplate) {
        Page<TrMenuTemplate> page = new Page(pageNo, pageSize);
        LambdaQueryWrapper<TrMenuTemplate> queryWrapper = Wrappers.lambdaQuery();
        if(StrUtil.isNotBlank(trMenuTemplate.getTemplateName())){
            queryWrapper.like(TrMenuTemplate::getTemplateName, trMenuTemplate.getTemplateName());
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public TrMenuTemplateVo getById(Long id) {
        TrMenuTemplateVo trMenuTemplateVo = new TrMenuTemplateVo();
        TrMenuTemplate trMenuTemplate = baseMapper.selectById(id);
        Optional.ofNullable(trMenuTemplate).orElseThrow(() -> new LibraException(TenantExceptionCode.MENU_TEMPLATE_NULL));
        BeanUtils.copyProperties(trMenuTemplate, trMenuTemplateVo);
        trMenuTemplateVo.setMenuListIds(selectMenuTemplateRef(trMenuTemplate));
        return trMenuTemplateVo;
    }

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        TrMenuTemplate trMenuTemplate = baseMapper.selectById(id);
        Optional.ofNullable(trMenuTemplate).orElseThrow(() -> new LibraException(TenantExceptionCode.MENU_TEMPLATE_NULL));

        // 校验模板是否与租户关联，若关联无法删除，需解绑完，即可删除
        TrTenantParams trTenantParams = new TrTenantParams();
        trTenantParams.setMenuTemplateId(trMenuTemplate.getId());
        List<TrTenant> list = trTenantService.select(trTenantParams);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new LibraException(TenantExceptionCode.MENU_TEMPLATE_BIND_TANANT);
        }
        return baseMapper.deleteById(id);
    }

    /**
     * 查询列表
     *
     * @param trMenuTemplate
     * @return
     */
    @Override
    public List<TrMenuTemplate> list(TrMenuTemplate trMenuTemplate) {
        return baseMapper.selectList(null);
    }

    /**
     * 同步租户菜单权限缓存
     *
     * @param id
     * @return int
     * @description
     * @author huangfubin
     * @date 2021/8/22
     */
    @Override
    public int syncTenantMenuPermission(Long id) {
        TrMenuTemplate trMenuTemplate = baseMapper.selectById(id);
        Optional.ofNullable(trMenuTemplate).orElseThrow(() -> new LibraException(TenantExceptionCode.MENU_TEMPLATE_NULL));
        TrTenantParams trTenantParams = new TrTenantParams();
        trTenantParams.setMenuTemplateId(id);
        List<TrTenant> trTenants = trTenantService.select(trTenantParams);
        List<Long> menuIds = selectMenuTemplateRef(trMenuTemplate);
        // 刷新redis缓存数据
        trTenants.forEach(data -> redisUtil.strSet(TenantRedisKeyConstant.TENANT_MENU + data.getTenantCode(), menuIds));

        // 重新设置租户默认管理员权限
        InitTenantParams initTenantParams = new InitTenantParams();
        initTenantParams.setMenuIds(menuIds);
        initTenantParams.setTenantCodes(trTenants.stream().map(TrTenant::getTenantCode).collect(Collectors.toList()));
        Integer count = FeignResponseUtil.getThrow(baseInitTenantServiceClient.syncAdminRoleMenuPermission(initTenantParams));
        if (count == null || trTenants.size() != count) {
            log.warn("缓存刷新条数：{}，数据库更新条数：{}", trTenants.size(), count);
        }
        return trTenants.size();
    }

    /**
     * @param trMenuTemplate
     * @return java.util.List<java.lang.Long>
     * @description 查询菜单模版关联菜单ids
     * @author huangfubin
     * @date 2021/8/22
     */
    public List<Long> selectMenuTemplateRef(TrMenuTemplate trMenuTemplate) {
        List<TrMenuTemplateRef> list = trMenuTemplateRefMapper.selectList(Wrappers.<TrMenuTemplateRef>lambdaQuery()
                .eq(TrMenuTemplateRef::getMenuTemplateId, trMenuTemplate.getId())
                .select(TrMenuTemplateRef::getMenuId));
        return list.stream().map(TrMenuTemplateRef::getMenuId).collect(Collectors.toList());
    }

    /**
     * 新增数据
     *
     * @param trMenuTemplateParams
     * @return
     */
    @Override
    public int insert(TrMenuTemplateParams trMenuTemplateParams) {
        TrMenuTemplate trMenuTemplate = new TrMenuTemplate();
        BeanUtils.copyProperties(trMenuTemplateParams, trMenuTemplate);
        baseMapper.insert(trMenuTemplate);
        trMenuTemplateParams.setId(trMenuTemplate.getId());
        if (CollectionUtil.isNotEmpty(trMenuTemplateParams.getMenuListIds())) {
            addTrMenuTemplateRef(trMenuTemplateParams);
        }
        return 1;
    }

    /**
     * 更新数据(为null的字段不会更新)
     *
     * @param trMenuTemplateParams
     * @return
     */
    @Override
    public int update(TrMenuTemplateParams trMenuTemplateParams) {
        TrMenuTemplate trMenuTemplate = new TrMenuTemplate();
        BeanUtils.copyProperties(trMenuTemplateParams, trMenuTemplate);
        if (CollectionUtil.isNotEmpty(trMenuTemplateParams.getMenuListIds())) {
            trMenuTemplateRefMapper.delete(Wrappers.<TrMenuTemplateRef>lambdaQuery()
                    .eq(TrMenuTemplateRef::getMenuTemplateId, trMenuTemplate.getId()));
            addTrMenuTemplateRef(trMenuTemplateParams);
        }
        return baseMapper.updateById(trMenuTemplate);
    }

    /**
     * 新增关联关系
     *
     * @param trMenuTemplateParams
     */
    private void addTrMenuTemplateRef(TrMenuTemplateParams trMenuTemplateParams) {
        List<TrMenuTemplateRef> list = new ArrayList<>();
        trMenuTemplateParams.getMenuListIds().forEach(data -> {
            TrMenuTemplateRef trMenuTemplateRef = new TrMenuTemplateRef();
            trMenuTemplateRef.setMenuId(data);
            trMenuTemplateRef.setMenuTemplateId(trMenuTemplateParams.getId());
            trMenuTemplateRef.setDelFlag(0);
            list.add(trMenuTemplateRef);
        });
        trMenuTemplateRefMapper.insertBatchSomeColumn(list);
    }

    /**
     * 更新数据(为null的字段会更新)
     *
     * @param trMenuTemplate
     * @return
     */
    @Override
    public int updateSomeColumnById(TrMenuTemplate trMenuTemplate) {
        return baseMapper.alwaysUpdateSomeColumnById(trMenuTemplate);
    }
}
