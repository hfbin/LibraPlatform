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
import cn.hfbin.ucpm.enums.RelationTypeEnum;
import cn.hfbin.ucpm.mapper.RelationRoleMapper;
import cn.hfbin.ucpm.params.RelationRoleParams;
import cn.hfbin.ucpm.service.RelationRoleService;
import cn.hfbin.ucpm.vo.RelationRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 角色关联表
 * @Date: 2021-06-16
 */
@Service
public class RelationRoleServiceImpl extends ServiceImpl<RelationRoleMapper, RelationRole> implements RelationRoleService {

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param relationRole
     * @return
     */
    @Override
    public Page<RelationRole> page(Integer pageNo, Integer pageSize, RelationRole relationRole) {
        Page<RelationRole> page = new Page(pageNo, pageSize);
        return baseMapper.selectPage(page, Wrappers.lambdaQuery(relationRole));
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public RelationRole getById(Long id) {
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
     * 根据不同类型，查询对应关联数据
     * @param relationRoleParams
     * @return
     */
    @Override
    public List<RelationRoleVo> selectList(RelationRoleParams relationRoleParams) {
        RelationTypeEnum type = relationRoleParams.getRelationTypeEnum();
        switch (type) {
            case USER_ID:
                return baseMapper.selectRoleListByEmpId(relationRoleParams);
            case USER_GROUP:
                break;
            case DEPT_GROUP:
                break;
            case POSITION_GROUP:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + relationRoleParams.getRelationTypeEnum().getCode());
        }
        return null;
    }

    /**
     * 根据角色id查询关联数据
     * @param roleId 角色id
     * @return  List<RelationRole>
     */
    @Override
    public List<RelationRole> selectListByRoleId(Long roleId) {
        return baseMapper.selectList(Wrappers.<RelationRole>lambdaQuery().eq(RelationRole::getRoleId, roleId));
    }

    /**
     * 新增数据
     * @param relationRole
     * @return
     */
    @Override
    public int insert(RelationRole relationRole) {
        return baseMapper.insert(relationRole);
    }

    /**
     * 更新数据(为null的字段不会更新)
     * @param relationRole
     * @return
     */
    @Override
    public int update(RelationRole relationRole) {
        return baseMapper.updateById(relationRole);
    }

    /**
     * 更新数据(为null的字段会更新)
     * @param relationRole
     * @return
     */
    @Override
    public int updateSomeColumnById(RelationRole relationRole) {
        return baseMapper.alwaysUpdateSomeColumnById(relationRole);
    }

    /**
     * 批量插入(为null的字段会更新)
     * @param baseRelationRoleList
     * @return
     */
    @Override
    public int insertBatchSomeColumn(List<RelationRole> baseRelationRoleList) {
        return baseMapper.insertBatchSomeColumn(baseRelationRoleList);
    }

    /**
     * 根据关联id删除
     * @param relationRoleParams
     * @return
     */
    @Override
    public int deleteByRelationId(RelationRoleParams relationRoleParams) {
        LambdaQueryWrapper<RelationRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RelationRole::getRelationId, relationRoleParams.getId());
        return baseMapper.delete(queryWrapper);
    }
}

