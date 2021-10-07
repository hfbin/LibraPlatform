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

package cn.hfbin.common.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @Author: huangfubin
 * @Description: mybatis plus mapper层选装件 https://mp.baomidou.com/guide/crud-interface.html#select
 * @Date: 2021/6/10
 */
public interface SuperMapper<T> extends BaseMapper<T> {


    /**
     * 批量插入 仅适用于mysql
     * @param entityList 实体列表
     */
    int insertBatchSomeColumn(Collection<T> entityList);

    /**
     * 根据 ID 更新固定的那几个字段(不包含逻辑删除),解决设置为null字段无法更新问题
     * @param entity
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);
}
