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

package cn.hfbin.ucpm.controller;

import cn.hfbin.ucpm.api.VersionApiService;
import cn.hfbin.ucpm.entity.Version;
import cn.hfbin.ucpm.service.VersionService;
import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.common.security.annotation.PreAuthorize;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: huangfubin
 * @Description: 版本管理
 * @Date: 2021-08-27
 */
@RestController
@RequestMapping("/version" )
@Api(tags = "版本管理管理")
public class VersionController implements VersionApiService {

    @Autowired
    private VersionService versionService;

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param version 版本管理
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "分页查询数据", notes = "分页查询数据")
    @PreAuthorize("version:page")
    public ResponseData<Page<Version>> page(Integer pageNo,
                                            Integer pageSize,
                                            Version version) {
        return ResponseData.ok(versionService.page(pageNo, pageSize, version));
    }


    /**
     * 通过id查询版本管理
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @PreAuthorize("version:info")
    public ResponseData<Version> getById(Long id) {
        return ResponseData.ok(versionService.getById(id));
    }

    /**
     * 新增版本管理
     * @param version 版本管理
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "新增版本管理", notes = "新增版本管理")
    @PreAuthorize("version:save")
    public ResponseData<Integer> save(Version version) {
        return ResponseData.ok(versionService.insert(version));
    }

    /**
     * 修改版本管理
     * @param version 版本管理
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "更新版本管理", notes = "更新版本管理")
    @PreAuthorize("version:update")
    public ResponseData<Integer> updateById(Version version) {
        return ResponseData.ok(versionService.update(version));
    }

    /**
     * 通过id删除版本管理
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id删除版本管理", notes = "通过id删除版本管理")
    @PreAuthorize("version:delete")
    public ResponseData<Integer> removeById(Long id) {
        return ResponseData.ok(versionService.deleteById(id));
    }

}
