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

import cn.hfbin.common.log.enums.LogTypeEnum;
import cn.hfbin.common.log.enums.OptBehaviorEnum;
import cn.hfbin.ucpm.api.PositionApiService;
import cn.hfbin.ucpm.entity.Position;
import cn.hfbin.ucpm.params.PositionParams;
import cn.hfbin.ucpm.service.PositionService;
import cn.hfbin.ucpm.vo.PositionVo;
import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.common.log.annotation.Log;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 岗位表
 * @Date: 2021-06-16
 */
@RestController
@RequestMapping("/position" )
@Api(tags = "岗位管理")
public class PositionController implements PositionApiService {

    @Autowired
    private PositionService positionService;

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param position 岗位表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "分页查询数据", notes = "分页查询数据")
    @Log(desc = "岗位模块-分页查询", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.SELECT)
    public ResponseData<Page<Position>> page(Integer pageNo,
                                             Integer pageSize,
                                             Position position) {
        return ResponseData.ok(positionService.page(pageNo, pageSize, position));
    }

    /**
     * 查询岗位列表（不分页）
     * @param positionParams
     * @return
     */
    @Override
    @ApiOperation(value = "查询岗位列表（不分页）", notes = "查询岗位列表（不分页）")
    @Log(desc = "岗位模块-查询", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.SELECT)
    public ResponseData<List<PositionVo>> list(PositionParams positionParams) {
        return ResponseData.ok(positionService.list(positionParams));

    }


    /**
     * 通过id查询岗位表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @Log(desc = "岗位模块-id查询", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.SELECT)
    public ResponseData<PositionVo> getById(Long id) {
        Position position = positionService.getById(id);
        PositionVo positionVo = new PositionVo();
        BeanUtils.copyProperties(position, positionVo);
        return ResponseData.ok(positionVo);
    }

    /**
     * 新增岗位表
     * @param basePosition 岗位表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "新增岗位表", notes = "新增岗位表")
    @Log(desc = "岗位模块-新增", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.ADD)
    public ResponseData<Integer> save(PositionParams basePosition) {
        return ResponseData.ok(positionService.insert(basePosition));
    }

    /**
     * 修改岗位表
     * @param basePosition 岗位表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "更新岗位表", notes = "更新岗位表")
    @Log(desc = "岗位模块-更新", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.UPDATE)
    public ResponseData<Integer> updateById(PositionParams basePosition) {
        return ResponseData.ok(positionService.update(basePosition));
    }

    /**
     * 通过id删除岗位表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id删除岗位表", notes = "通过id删除岗位表")
    @Log(desc = "岗位模块-删除", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.DELETE)
    public ResponseData<Integer> removeById(Long id) {
        return ResponseData.ok(positionService.deleteById(id));
    }

}
