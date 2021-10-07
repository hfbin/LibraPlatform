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


import cn.hfbin.ucpm.entity.Position;
import cn.hfbin.ucpm.params.PositionParams;
import cn.hfbin.ucpm.vo.PositionVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 岗位表
 * @Date: 2021-06-16
 */
public interface PositionService {
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param position
     * @return
     */
    Page<Position> page(Integer pageNo, Integer pageSize, Position position);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Position getById(Long id);

    /**
     * 新增数据
     * @param basePosition
     * @return
     */
    int insert(PositionParams basePosition);

    /**
     * 更新数据(为null的字段不会更新)
     * @param basePosition
     * @return
     */
    int update(PositionParams basePosition);

    /**
     * 更新数据
     * @param position
     * @return
     */
    int updateSomeColumnById(Position position);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 查询岗位列表（不分页）
     * @param positionParams
     * @return
     */
    List<PositionVo> list(PositionParams positionParams);

    /**
     * 查询岗位列表（可扩展通用）
     * @param positionParams
     * @return
     */
    List<Position> selectList(PositionParams positionParams);
}
