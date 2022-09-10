package cn.hfbin.auth.controller;

import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.auth.constant.AuthRedisKeyConstant;
import cn.hfbin.common.core.constant.SpecialCharacterPool;
import cn.hfbin.common.core.context.SpringContextUtils;
import cn.hfbin.common.core.utils.FeignResponseUtil;
import cn.hfbin.common.redis.util.RedisUtil;
import cn.hfbin.ucpm.client.MenuServiceClient;
import cn.hfbin.ucpm.params.TreeParams;
import cn.hfbin.ucpm.vo.PermissionResourceVo;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Author: huangfubin
 * @Description: MenuResourceController 类
 * @Date: 2021/8/9
 */
@RestController
@Api(tags = "权限模块")
public class MenuResourceController {

    @Autowired
    private MenuServiceClient menuServiceClient;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/menu-resource")
    @ApiOperation(value = "查询用户资源权限", notes = "查询用户资源权限")
    public ResponseData<PermissionResourceVo> menuResource(){
        TreeParams treeParams = new TreeParams();
        PermissionResourceVo permissionResourceVo = FeignResponseUtil.get(menuServiceClient.queryUserMenu(treeParams));
        if(Objects.isNull(permissionResourceVo)){
            return ResponseData.ok();
        }
        if(CollectionUtil.isNotEmpty(permissionResourceVo.getInterfaces())){
            redisUtil.strSet(AuthRedisKeyConstant.USER_INTERFACE_KEY + SpringContextUtils.getClientCode() + SpecialCharacterPool.S_COLON + SpringContextUtils.getIdentityId(), JSONObject.toJSONString(permissionResourceVo.getInterfaces()));
        }
        permissionResourceVo.setInterfaces(null);
        return ResponseData.ok(permissionResourceVo);
    }

    @GetMapping("/interface-per")
    @ApiOperation(value = "查询用户资源权限", notes = "查询用户接口权限code")
    public ResponseData<List<String>> interfacePer(){
        String interfaceCode =  (String) redisUtil.strGet(AuthRedisKeyConstant.USER_INTERFACE_KEY + SpringContextUtils.getClientCode() + SpecialCharacterPool.S_COLON + SpringContextUtils.getIdentityId());
        if(StringUtils.isEmpty(interfaceCode)){
            return ResponseData.ok();
        }
        List<String> lists = JSONObject.parseObject(interfaceCode, List.class);
        return ResponseData.ok(lists);
    }
}
