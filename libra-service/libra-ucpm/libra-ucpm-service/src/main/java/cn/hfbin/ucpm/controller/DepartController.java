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


import cn.hfbin.common.log.annotation.Log;
import cn.hfbin.common.log.enums.LogTypeEnum;
import cn.hfbin.common.log.enums.OptBehaviorEnum;
import cn.hfbin.ucpm.api.DepartApiService;
import cn.hfbin.ucpm.entity.Depart;
import cn.hfbin.ucpm.params.DepartParams;
import cn.hfbin.ucpm.service.DepartService;
import cn.hfbin.ucpm.vo.DepartTreeVo;
import cn.hfbin.ucpm.vo.DepartVo;
import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.common.security.annotation.PreAuthorize;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 组织机构表
 * @Date: 2021-06-16
 */
@PreAuthorize("dept")
@RestController
@RequestMapping("/depart" )
@Api(tags = "组织机构管理")
public class DepartController implements DepartApiService {

    @Autowired
    private DepartService departService;

    /**
     *  分页查询部门
     * @description
     * @param pageNo
     * @param pageSize
     * @param baseDepart
     * @author huangfubin
     * @date 2021/7/17
     * @return cn.hfbin.common.core.api.ResponseData<com.baomidou.mybatisplus.extension.plugins.pagination.Page<BaseDepart>>
     */
    @Override
    @PreAuthorize("dept:list")
    @ApiOperation(value = "部门列表", notes = "部门列表")
    @Log(desc = "部门管理-部门列表", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.SELECT)
    public ResponseData<Page<Depart>> page(Integer pageNo, Integer pageSize, DepartParams baseDepart) {
        return ResponseData.ok(departService.page(pageNo, pageSize, baseDepart));
    }

    /**
     *
     * @description 部门列表（树结构）
     * @param departParams
     * @author huangfubin
     * @date 2021/7/6
     * @return cn.hfbin.common.core.api.ResponseData<java.util.List<DepartTreeVo>>
     */
    @Override
    @PreAuthorize("dept:tree")
    @ApiOperation(value = "部门列表（树结构）", notes = "部门列表")
    @Log(desc = "部门管理-部门列表（树结构）", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.SELECT)
    public ResponseData<List<DepartTreeVo>> list(DepartParams departParams) {
        return ResponseData.ok(departService.treeList(departParams));
    }
    /**
     *
     * @description 查询一级部门
     * @param departParams
     * @author huangfubin
     * @date 2021/7/6
     * @return cn.hfbin.common.core.api.ResponseData<java.util.List<DepartVo>>
     */
    @Override
    @ApiOperation(value = "查询一级部门(若传id则查询对应子部门)", notes = "查询一级部门(若传id则查询对应子部门)")
    public ResponseData<List<DepartVo>> firstTreeList(DepartParams departParams) {
        return ResponseData.ok(departService.firstTreeList(departParams));
    }

    /**
     * 通过id查询组织机构表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @Log(desc = "部门管理-根据id查询", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.SELECT)
    public ResponseData<DepartVo> getById(Long id) {
        return ResponseData.ok(departService.selectById(id));
    }

    /**
     * 新增组织机构表
     * @param depart 组织机构表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "新增组织机构表", notes = "新增组织机构表")
    @Log(desc = "部门管理-新增", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.ADD)
    public ResponseData<Integer> save(Depart depart) {
        return ResponseData.ok(departService.insert(depart));
    }

    /**
     * 修改组织机构表
     * @param depart 组织机构表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "更新组织机构表", notes = "更新组织机构表")
    @Log(desc = "部门管理-更新", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.UPDATE)
    public ResponseData<Integer> updateById(Depart depart) {
        return ResponseData.ok(departService.update(depart));
    }

    /**
     * 通过id删除组织机构表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id删除组织机构表", notes = "通过id删除组织机构表")
    @Log(desc = "部门管理-删除", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.DELETE)
    public ResponseData<Integer> removeById(Long id) {
        return ResponseData.ok(departService.deleteById(id));
    }

}
