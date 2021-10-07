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

package cn.hfbin.base.service.impl;

import cn.hfbin.base.entity.OptLog;
import cn.hfbin.base.mapper.OptLogMapper;
import cn.hfbin.base.service.OptLogService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: huangfubin
 * @Description: 系统操作日志表
 * @Date: 2021-08-31
 */
@Service
public class OptLogServiceImpl extends ServiceImpl<OptLogMapper, OptLog> implements OptLogService {

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param optLog
     * @return
     */
    @Override
    public Page<OptLog> page(Integer pageNo, Integer pageSize, OptLog optLog) {
        Page<OptLog> page = new Page(pageNo, pageSize);
        return baseMapper.selectPage(page, Wrappers.lambdaQuery(optLog).orderByDesc(OptLog::getCreateTime));
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public OptLog getById(Long id) {
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
     * 新增数据
     * @param optLog
     * @return
     */
    @Override
    public int insert(OptLog optLog) {
        return baseMapper.insert(optLog);
    }

    /**
     * 更新数据(为null的字段不会更新)
     * @param optLog
     * @return
     */
    @Override
    public int update(OptLog optLog) {
        return baseMapper.updateById(optLog);
    }

    /**
     * 更新数据(为null的字段会更新)
     * @param optLog
     * @return
     */
    @Override
    public int updateSomeColumnById(OptLog optLog) {
        return baseMapper.alwaysUpdateSomeColumnById(optLog);
    }
}
