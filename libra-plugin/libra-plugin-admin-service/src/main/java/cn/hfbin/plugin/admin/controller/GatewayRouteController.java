package cn.hfbin.plugin.admin.controller;

import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.plugin.admin.dto.GatewayRouteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/5/1 4:13 下午
 * @description: 网关动态路由
 */
@Api(tags = "网关动态路由")
@RestController
@RequestMapping("/route")
public class GatewayRouteController {

    @ApiOperation("新增网关路由")
    @PostMapping("/add")
    public ResponseData<Object> add(@RequestBody GatewayRouteDto blueGreenGrayDto) {
        return ResponseData.ok();
    }

    @ApiOperation("更新网关路由")
    @PostMapping("/update")
    public ResponseData<Object> update(@RequestBody GatewayRouteDto blueGreenGrayDto) {
        return ResponseData.ok();
    }

    @ApiOperation("查询网关路由")
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
