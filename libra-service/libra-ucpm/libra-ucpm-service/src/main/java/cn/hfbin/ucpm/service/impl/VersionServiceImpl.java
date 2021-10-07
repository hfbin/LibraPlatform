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

import cn.hfbin.ucpm.entity.Version;
import cn.hfbin.ucpm.mapper.VersionMapper;
import cn.hfbin.ucpm.service.VersionService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: huangfubin
 * @Description: 版本管理
 * @Date: 2021-08-27
 */
@Service
public class VersionServiceImpl extends ServiceImpl<VersionMapper, Version> implements VersionService {

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param version
     * @return
     */
    @Override
    public Page<Version> page(Integer pageNo, Integer pageSize, Version version) {
        Page<Version> page = new Page(pageNo, pageSize);
        return baseMapper.selectPage(page, Wrappers.lambdaQuery(version));
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Version getById(Long id) {
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
     * @param version
     * @return
     */
    @Override
    public int insert(Version version) {
        return baseMapper.insert(version);
    }

    /**
     * 更新数据(为null的字段不会更新)
     * @param version
     * @return
     */
    @Override
    public int update(Version version) {
        return baseMapper.updateById(version);
    }

    /**
     * 更新数据(为null的字段会更新)
     * @param version
     * @return
     */
    @Override
    public int updateSomeColumnById(Version version) {
        return baseMapper.alwaysUpdateSomeColumnById(version);
    }
}
