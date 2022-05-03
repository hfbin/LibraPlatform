package cn.hfbin.plugin.admin.controller;

import cn.hfbin.common.core.api.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/5/4 12:11 上午
 * @description:
 */
@Api(tags = "公共")
@RestController
@RequestMapping("/common")
public class CommonController {

    @ApiOperation("查询服务列表")
    @GetMapping("/service-list")
    public ResponseData<Object> serviceList(@RequestParam(required = false, defaultValue = "1") Integer serviceType) {

        return ResponseData.ok();
    }
}
