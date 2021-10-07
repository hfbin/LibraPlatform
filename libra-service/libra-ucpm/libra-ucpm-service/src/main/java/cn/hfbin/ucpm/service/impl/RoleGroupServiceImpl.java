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

import cn.hfbin.ucpm.entity.RoleGroup;
import cn.hfbin.ucpm.enums.UcPmExceptionCode;
import cn.hfbin.ucpm.mapper.RoleGroupMapper;
import cn.hfbin.ucpm.service.RoleGroupService;
import cn.hfbin.common.core.exception.LibraException;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: huangfubin
 * @Description: 角色组
 * @Date: 2021-06-16
 */
@Service
public class RoleGroupServiceImpl extends ServiceImpl<RoleGroupMapper, RoleGroup> implements RoleGroupService {

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param roleGroup
     * @return
     */
    @Override
    public Page<RoleGroup> page(Integer pageNo, Integer pageSize, RoleGroup roleGroup) {
        Page<RoleGroup> page = new Page(pageNo, pageSize);
        return baseMapper.selectPage(page, Wrappers.lambdaQuery(roleGroup));
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public RoleGroup getById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        RoleGroup roleGroup = baseMapper.selectById(id);
        Optional.ofNullable(roleGroup).orElseThrow(() -> new LibraException(UcPmExceptionCode.ROLE_NULL));
        if(roleGroup.getSystemDefault() == 1) {
            throw new LibraException(UcPmExceptionCode.IDENTITY_EMPLOYEE_ADMIN);
        }
        return baseMapper.deleteById(id);
    }

    /**
     * 新增数据
     * @param roleGroup
     * @return
     */
    @Override
    public int insert(RoleGroup roleGroup) {
        return baseMapper.insert(roleGroup);
    }

    /**
     * 更新数据(为null的字段不会更新)
     * @param roleGroup
     * @return
     */
    @Override
    public int update(RoleGroup roleGroup) {
        return baseMapper.updateById(roleGroup);
    }

    /**
     * 更新数据(为null的字段会更新)
     * @param roleGroup
     * @return
     */
    @Override
    public int updateSomeColumnById(RoleGroup roleGroup) {
        return baseMapper.alwaysUpdateSomeColumnById(roleGroup);
    }
}
