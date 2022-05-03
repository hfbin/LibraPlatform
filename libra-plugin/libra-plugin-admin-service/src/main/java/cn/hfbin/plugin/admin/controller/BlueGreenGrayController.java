package cn.hfbin.plugin.admin.controller;

import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.plugin.admin.dto.BlueGreenGrayDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/5/1 4:15 下午
 * @description: 蓝绿发布
 */
@Api(tags = "蓝绿灰度发布策略")
@RestController
@RequestMapping("/blur-green-gray")
public class BlueGreenGrayController {

    @ApiOperation("新增全链路蓝绿灰度")
    @PostMapping("/add")
    public ResponseData<Object> add(@RequestBody BlueGreenGrayDto blueGreenGrayDto) {
        return ResponseData.ok();
    }

    @ApiOperation("更新全链路蓝绿灰度")
    @PostMapping("/update")
    public ResponseData<Object> update(@RequestBody BlueGreenGrayDto blueGreenGrayDto) {
        return ResponseData.ok();
    }

    @ApiOperation("查询全链路蓝绿灰度")
    @GetMapping("/page")
    public ResponseData<Object> page(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return ResponseData.ok();
    }

    @ApiOperation("禁用")
    @PostMapping("/enable")
    public ResponseData<Object> doEnable(@RequestParam(value = "id") Long id) {

        return ResponseData.ok();
    }

    @ApiOperation("发布蓝绿灰度策略")
    @PostMapping("/publish")
    public ResponseData<Object> publish() {

        return ResponseData.ok();
    }



}
