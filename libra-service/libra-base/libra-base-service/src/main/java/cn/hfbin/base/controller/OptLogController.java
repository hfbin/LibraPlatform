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

package cn.hfbin.base.controller;


import cn.hfbin.base.api.OptLogApiService;
import cn.hfbin.base.entity.OptLog;
import cn.hfbin.base.service.OptLogService;
import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.common.security.annotation.PreAuthorize;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: huangfubin
 * @Description: 系统操作日志表
 * @Date: 2021-08-31
 */
@RestController
@RequestMapping("/optlog")
@Api(tags = "系统操作日志表管理")
public class OptLogController implements OptLogApiService {

    @Autowired
    private OptLogService optLogService;

    /**
     * 分页查询
     *
     * @param pageNo   页码
     * @param pageSize 页数
     * @param optLog   系统操作日志表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "分页查询数据", notes = "分页查询数据")
    @PreAuthorize(value = "optlog:page", enabled = false)
    public ResponseData<Page<OptLog>> page(Integer pageNo,
                                           Integer pageSize,
                                           OptLog optLog) {
        return ResponseData.ok(optLogService.page(pageNo, pageSize, optLog));
    }


    /**
     * 通过id查询系统操作日志表
     *
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @PreAuthorize(value = "optlog:info", enabled = false)
    public ResponseData<OptLog> getById(Long id) {
        return ResponseData.ok(optLogService.getById(id));
    }

    /**
     * 新增系统操作日志表
     *
     * @param optLog 系统操作日志表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "新增系统操作日志表", notes = "新增系统操作日志表")
    @PreAuthorize(value = "optlog:save", enabled = false)
    public ResponseData<Integer> save(OptLog optLog) {
        return ResponseData.ok(optLogService.insert(optLog));
    }

    /**
     * 修改系统操作日志表
     *
     * @param optLog 系统操作日志表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "更新系统操作日志表", notes = "更新系统操作日志表")
    @PreAuthorize(value = "optlog:update", enabled = false)
    public ResponseData<Integer> updateById(OptLog optLog) {
        return ResponseData.ok(optLogService.update(optLog));
    }

    /**
     * 通过id删除系统操作日志表
     *
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id删除系统操作日志表", notes = "通过id删除系统操作日志表")
    @PreAuthorize(value = "optlog:delete", enabled = false)
    public ResponseData<Integer> removeById(Long id) {
        return ResponseData.ok(optLogService.deleteById(id));
    }

}
