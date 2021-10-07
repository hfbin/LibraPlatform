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

import cn.hfbin.ucpm.entity.RoleGroup;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Author: huangfubin
 * @Description: 角色组
 * @Date: 2021-06-16
 */
public interface RoleGroupService {
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param roleGroup
     * @return
     */
    Page<RoleGroup> page(Integer pageNo, Integer pageSize, RoleGroup roleGroup);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    RoleGroup getById(Long id);

    /**
     * 新增数据
     * @param roleGroup
     * @return
     */
    int insert(RoleGroup roleGroup);

    /**
     * 更新数据(为null的字段不会更新)
     * @param roleGroup
     * @return
     */
    int update(RoleGroup roleGroup);

    /**
     * 更新数据
     * @param roleGroup
     * @return
     */
    int updateSomeColumnById(RoleGroup roleGroup);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(Long id);
}
