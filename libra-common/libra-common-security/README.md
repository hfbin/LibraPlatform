# libra-common-security 说明
1、此组件主要实现了用户鉴权，Openfeign调用默认不鉴权，在接口层方法未添加`@PreAuthorize`注解也不会进行鉴权。还提供了全局配置`libra.security.enabled`，默认是开启鉴权的，如果在配置中配置为`false`则不会鉴权。

2、此组件默认依赖了`libra-common-cache`组件，在鉴权时候是在redis缓存中获取对应用户权限进行比对鉴权
# libra-common-security 接入说明
1、在对应服务pom.xml文件引入对应依赖
```xml
<dependency>
  <groupId>cn.hfbin</groupId>
  <artifactId>libra-common-security</artifactId>
</dependency>
```
# libra-common-security 使用说明
在对应接口层类或者方法上添加注解和对应标识即可，比如`@PreAuthorize("emp:list")`
```java
@RestController
@RequestMapping("/identity/employee" )
@Api(tags = "员工身份信息管理")
public class IdentityEmployeeController implements IdentityEmployeeApiService {

    @Autowired
    private IdentityEmployeeService identityEmployeeService;

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页数
     * @param employeeQueryParams 员工身份信息表
     * @return ResponseData
     */
    @Override
    @PreAuthorize("emp:list")
    @ApiOperation(value = "分页查询数据", notes = "分页查询数据")
    @Log(desc = "员工管理-分页查询", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.SELECT)
    public ResponseData<Page<IdentityInfoVo>> page(Integer pageNo,
                                                   Integer pageSize,
                                                   EmployeeQueryParams employeeQueryParams) {
        return ResponseData.ok(identityEmployeeService.page(pageNo, pageSize, employeeQueryParams));
    }
}

```

