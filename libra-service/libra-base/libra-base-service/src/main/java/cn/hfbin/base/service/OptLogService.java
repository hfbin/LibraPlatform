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

package cn.hfbin.base.service;

import cn.hfbin.base.entity.OptLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Author: huangfubin
 * @Description: 系统操作日志表
 * @Date: 2021-08-31
 */
public interface OptLogService{
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param optLog
     * @return
     */
    Page<OptLog> page(Integer pageNo, Integer pageSize, OptLog optLog);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    OptLog getById(Long id);

    /**
     * 新增数据
     * @param optLog
     * @return
     */
    int insert(OptLog optLog);

    /**
     * 更新数据(为null的字段不会更新)
     * @param optLog
     * @return
     */
    int update(OptLog optLog);

    /**
     * 更新数据
     * @param optLog
     * @return
     */
    int updateSomeColumnById(OptLog optLog);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(Long id);
}
