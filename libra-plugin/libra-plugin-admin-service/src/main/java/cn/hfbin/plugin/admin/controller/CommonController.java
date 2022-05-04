package cn.hfbin.plugin.admin.controller;

import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.plugin.common.enums.ServiceTypeEnum;
import cn.hfbin.plugin.common.instance.ServiceInstanceResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private ServiceInstanceResource serviceInstanceResource;

    @ApiOperation("查询服务列表")
    @GetMapping("/service-list")
    public ResponseData<Object> serviceList(@RequestParam(required = false) Integer serviceType) {
        List<String> allServiceList;
        if (ServiceTypeEnum.GATEWAY.getCode() == serviceType) {
            allServiceList = serviceInstanceResource.getGatewayList();
        } else if (ServiceTypeEnum.SERVICE.getCode() == serviceType) {
            allServiceList = serviceInstanceResource.getServiceList();
        } else {
            allServiceList = serviceInstanceResource.getAllServiceList();
        }
        return ResponseData.ok(allServiceList);
    }

    @ApiOperation("根据服务名查询服务信息")
    @GetMapping("/service/{name}")
    public ResponseData<Object> serviceList(@PathVariable("name") String serviceName) {
        return ResponseData.ok(serviceInstanceResource.getServiceInstanceList(serviceName));
    }
}
