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
import cn.hfbin.tenant.entity.TrTenant;
import cn.hfbin.tenant.enums.TenantExceptionCode;
import cn.hfbin.tenant.mapper.TrTenantMapper;
import cn.hfbin.tenant.params.TrTenantParams;
import cn.hfbin.tenant.service.TrMenuTemplateService;
import cn.hfbin.tenant.service.TrTenantService;
import cn.hfbin.tenant.vo.TrMenuTemplateVo;
import cn.hfbin.tenant.vo.TrTenantVo;
import cn.hfbin.ucpm.client.InitTenantServiceClient;
import cn.hfbin.ucpm.params.InitTenantParams;
import cn.hfbin.ucpm.vo.InitTenantVo;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Author: huangfubin
 * @Description: 多租户信息表
 * @Date: 2021-06-16
 */
@Service
public class TrTenantServiceImpl extends ServiceImpl<TrTenantMapper, TrTenant> implements TrTenantService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TrMenuTemplateService trMenuTemplateService;

    @Autowired
    private InitTenantServiceClient baseInitTenantServiceClient;

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param baseTenant
     * @return
     */
    @Override
    public Page<TrTenantVo> page(Integer pageNo, Integer pageSize, TrTenant baseTenant) {
        Page<TrTenantVo> page = new Page(pageNo, pageSize);
        return baseMapper.selectPageList(page, baseTenant);
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public TrTenant getById(Long id) {
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
     * 根据条件查询租户
     * @param trTenantParams
     * @return
     */
    @Override
    public List<TrTenant> select(TrTenantParams trTenantParams) {
        LambdaQueryWrapper<TrTenant> query = Wrappers.<TrTenant>lambdaQuery();
        if(trTenantParams.getMenuTemplateId() != null){
            query.eq(TrTenant::getMenuTemplateId, trTenantParams.getMenuTemplateId());
        }
        if(trTenantParams.getId() != null){
            query.eq(TrTenant::getId, trTenantParams.getId());
        }
        return baseMapper.selectList(query);
    }

    /**
     * 根据code查询租户信息
     * @param tenantCode
     * @return
     */
    @Override
    public TrTenant selectByCode(String tenantCode) {
        return baseMapper.selectOne(Wrappers.<TrTenant>lambdaQuery().eq(TrTenant::getTenantCode, tenantCode));
    }

    /**
     * 新增数据
     * 1、创建默认部门
     * 2、创建角色及角色组
     * 3、将租户对应菜单模版的权限赋予管理员角色
     * 4、创建用户（账号及身份信息），绑定部门、角色
     * 5、创建租户
     * @param baseTenant
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TrTenantParams baseTenant) {
        // 查询并校验模板是否存在
        TrMenuTemplateVo trMenuTemplateVo = trMenuTemplateService.getById(baseTenant.getMenuTemplateId());
        InitTenantParams initTenantParams = new InitTenantParams();
        initTenantParams.setTenantCode(baseTenant.getTenantCode());
        initTenantParams.setTenantName(baseTenant.getTenantName());
        initTenantParams.setUsername(baseTenant.getUsername());
        initTenantParams.setPassword(baseTenant.getPassword());
        initTenantParams.setMobile(baseTenant.getMobile());
        initTenantParams.setMenuIds(trMenuTemplateVo.getMenuListIds());
        // 初始化账号、部门、角色
        InitTenantVo initTenantVo = FeignResponseUtil.getThrow(baseInitTenantServiceClient.init(initTenantParams));
        // 创建租户
        TrTenant trTenant = new TrTenant();
        BeanUtils.copyProperties(baseTenant, trTenant);
        trTenant.setIdentityId(initTenantVo.getIdentityId());
        return baseMapper.insert(trTenant);
    }

    /**
     * 菜单租户菜单
     * @param tenantCode
     * @return
     */
    @Override
    public List<Long> selectMenu(String tenantCode){
        List<Long> ids = (List<Long>) redisUtil.strGet(TenantRedisKeyConstant.TENANT_MENU + tenantCode);
        if(CollectionUtil.isNotEmpty(ids)){
            return ids;
        }
        List<Long> menuIds = baseMapper.selectMenu(tenantCode);
        redisUtil.strSet(TenantRedisKeyConstant.TENANT_MENU + tenantCode, menuIds);
        return menuIds;
    }

    /**
     * 更新数据(为null的字段不会更新)
     * @param baseTenant
     * @return
     */
    @Override
    public int update(TrTenant baseTenant) {
        TrTenant trTenant = baseMapper.selectById(baseTenant.getId());
        Optional.ofNullable(trTenant).orElseThrow(()->new LibraException(TenantExceptionCode.TENANT_IS_NULL));
        if(!baseTenant.getTenantCode().equals(trTenant.getTenantCode())){
            throw new LibraException(TenantExceptionCode.TENANT_CODE_ALREADY_EXISTS);
        }
        // 如果模板更新，则刷新租户菜单缓存
        if(!baseTenant.getMenuTemplateId().equals(trTenant.getMenuTemplateId())){
            // 查询模板并校验
            TrMenuTemplateVo trMenuTemplateVo = trMenuTemplateService.getById(baseTenant.getMenuTemplateId());
            Optional.ofNullable(trMenuTemplateVo).orElseThrow(() -> new LibraException(TenantExceptionCode.MENU_TEMPLATE_NULL));
            //查询模板关联菜单
            TrMenuTemplate trMenuTemplate = new TrMenuTemplate();
            BeanUtils.copyProperties(trMenuTemplateVo, trMenuTemplate);
            List<Long> menuIds = trMenuTemplateService.selectMenuTemplateRef(trMenuTemplate);
            // 刷新租户缓存中的数据
            redisUtil.strSet(TenantRedisKeyConstant.TENANT_MENU + trTenant.getTenantCode(), menuIds);
        }
        return baseMapper.updateById(baseTenant);
    }

    /**
     * 更新数据(为null的字段会更新)
     * @param baseTenant
     * @return
     */
    @Override
    public int updateSomeColumnById(TrTenant baseTenant) {
        return baseMapper.alwaysUpdateSomeColumnById(baseTenant);
    }
}
