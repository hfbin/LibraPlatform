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

import cn.hfbin.ucpm.api.InterfaceApiService;
import cn.hfbin.ucpm.entity.Interface;
import cn.hfbin.ucpm.params.MenuParams;
import cn.hfbin.ucpm.service.InterfaceService;
import cn.hfbin.ucpm.vo.TreeVo;
import cn.hfbin.common.core.api.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 接口表
 * @Date: 2021-06-18
 */
@RestController
@RequestMapping("/interface" )
@Api(tags = "接口管理")
public class InterfaceController implements InterfaceApiService {

    @Autowired
    private InterfaceService interfaceService;

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param anInterface 接口表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "分页查询数据", notes = "分页查询数据")
    public ResponseData<Page<Interface>> page(Integer pageNo,
                                              Integer pageSize,
                                              Interface anInterface) {
        return ResponseData.ok(interfaceService.page(pageNo, pageSize, anInterface));
    }

    /**
     * 分页查询
     * @param menuParams 接口表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "树结构", notes = "分页查询数据")
    public ResponseData<List<TreeVo>> page(MenuParams menuParams) {
        return ResponseData.ok(interfaceService.tree(menuParams));
    }

    /**
     * 查询所有接口数据
     * @param anInterface 接口
     * @return
     */
    @Override
    public ResponseData<List<Interface>> listAll(Interface anInterface) {
        return ResponseData.ok(interfaceService.list(anInterface));
    }


    /**
     * 通过id查询接口表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    public ResponseData<Interface> getById(Long id) {
        return ResponseData.ok(interfaceService.getById(id));
    }

    /**
     * 新增接口表
     * @param anInterface 接口表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "新增接口表", notes = "新增接口表")
    public ResponseData<Integer> save(Interface anInterface) {
        return ResponseData.ok(interfaceService.insert(anInterface));
    }

    /**
     * 修改接口表
     * @param anInterface 接口表
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "更新接口表", notes = "更新接口表")
    public ResponseData<Integer> updateById(Interface anInterface) {
        return ResponseData.ok(interfaceService.update(anInterface));
    }

    /**
     * 通过id删除接口表
     * @param id id
     * @return ResponseData
     */
    @Override
    @ApiOperation(value = "通过id删除接口表", notes = "通过id删除接口表")
    public ResponseData<Integer> removeById(Long id) {
        return ResponseData.ok(interfaceService.deleteById(id));
    }

}
