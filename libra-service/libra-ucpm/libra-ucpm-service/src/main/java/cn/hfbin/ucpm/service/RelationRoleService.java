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


import cn.hfbin.ucpm.entity.RelationRole;
import cn.hfbin.ucpm.params.RelationRoleParams;
import cn.hfbin.ucpm.vo.RelationRoleVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 角色关联表
 * @Date: 2021-06-16
 */
public interface RelationRoleService {
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param relationRole
     * @return
     */
    Page<RelationRole> page(Integer pageNo, Integer pageSize, RelationRole relationRole);

    /**
     * 根据不同类型，查询对应关联数据
     * @param relationRoleParams
     * @return
     */
    List<RelationRoleVo> selectList(RelationRoleParams relationRoleParams);

    /**
     * 根据角色id查询关联数据
     * @param roleId 角色id
     * @return @link RelationRole
     */
    List<RelationRole> selectListByRoleId(Long roleId);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    RelationRole getById(Long id);

    /**
     * 新增数据
     * @param relationRole
     * @return
     */
    int insert(RelationRole relationRole);

    /**
     * 更新数据(为null的字段不会更新)
     * @param relationRole
     * @return
     */
    int update(RelationRole relationRole);

    /**
     * 更新数据
     * @param relationRole
     * @return
     */
    int updateSomeColumnById(RelationRole relationRole);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 批量插入
     * @param baseRelationRoleList
     * @return
     */
    int insertBatchSomeColumn(List<RelationRole> baseRelationRoleList);

    /**
     * 根据关联id删除
     * @param relationRoleParams
     * @return
     */
    int deleteByRelationId(RelationRoleParams relationRoleParams);
}
