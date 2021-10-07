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


import cn.hfbin.ucpm.entity.IdentityPositionRef;
import cn.hfbin.ucpm.entity.Position;
import cn.hfbin.ucpm.enums.UcPmExceptionCode;
import cn.hfbin.ucpm.mapper.IdentityPositionRefMapper;
import cn.hfbin.ucpm.mapper.PositionMapper;
import cn.hfbin.ucpm.params.PositionParams;
import cn.hfbin.ucpm.service.PositionService;
import cn.hfbin.ucpm.vo.PositionVo;
import cn.hfbin.common.core.exception.LibraException;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: huangfubin
 * @Description: 岗位表
 * @Date: 2021-06-16
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {

    @Autowired
    IdentityPositionRefMapper identityPositionRefMapper;

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param position
     * @return
     */
    @Override
    public Page<Position> page(Integer pageNo, Integer pageSize, Position position) {
        Page<Position> page = new Page(pageNo, pageSize);
        LambdaQueryWrapper<Position> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Position::getUpdateTime);
        return baseMapper.selectPage(page, wrapper);
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Position getById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        Position position = baseMapper.selectById(id);
        Optional.ofNullable(position).orElseThrow( ()-> new LibraException(UcPmExceptionCode.POSITION_IS_NULL));
        LambdaQueryWrapper<IdentityPositionRef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(IdentityPositionRef::getPositionId, id);
        Integer count = identityPositionRefMapper.selectCount(queryWrapper);
        if(count > 0){
            throw new LibraException(UcPmExceptionCode.POSITION_EMP_EXIST);
        }
        return baseMapper.deleteById(id);
    }

    /**
     * 查询岗位列表（不分页）
     * @param positionParams
     * @return
     */
    @Override
    public List<PositionVo> list(PositionParams positionParams) {
        List<Position> positionList = baseMapper.selectList(null);
        List<PositionVo> positionVoList = new ArrayList<>();
        positionList.forEach(data -> {
            PositionVo positionVo = new PositionVo();
            BeanUtils.copyProperties(data, positionVo);
            positionVoList.add(positionVo);
        });
        return positionVoList;
    }

    /**
     * 查询岗位列表（可扩展通用）
     * @param positionIds
     * @return
     */
    @Override
    public List<Position> selectList(PositionParams positionIds) {
        LambdaQueryWrapper<Position> queryWrapper = new LambdaQueryWrapper<>();
        if(CollectionUtil.isNotEmpty(positionIds.getIds())){
            queryWrapper.in(Position::getId, positionIds.getIds());
        }
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增数据
     * @param basePosition
     * @return
     */
    @Override
    public int insert(PositionParams basePosition) {
        Position newPosition = new Position();
        BeanUtils.copyProperties(basePosition, newPosition);
        return baseMapper.insert(newPosition);
    }

    /**
     * 更新数据(为null的字段不会更新)
     * @param basePosition
     * @return
     */
    @Override
    public int update(PositionParams basePosition) {
        Position newPosition = new Position();
        BeanUtils.copyProperties(basePosition, newPosition);
        return baseMapper.updateById(newPosition);
    }

    /**
     * 更新数据(为null的字段会更新)
     * @param position
     * @return
     */
    @Override
    public int updateSomeColumnById(Position position) {
        return baseMapper.alwaysUpdateSomeColumnById(position);
    }
}
