# libra-common-log 说明
此组件主要实现了日志拦截，提供配置有：

1、提供日志记录开关`libra.log.enabled`默认开启，如果关闭则不处理相关逻辑

2、提供是否入库配置`libra.log.db`默认开启，如果开启了此模块需要依赖基础服务`libra-base-service`，需要在启动类添加@EnableFeignClients注解及扫描范围`@EnableFeignClients({TenantConstant.LIBRA_TENANT_PACKAGE})`
# libra-common-log 接入说明
1、在对应服务pom.xml文件引入对应依赖
```xml
<dependency>
  <groupId>cn.hfbin</groupId>
  <artifactId>libra-common-log</artifactId>
</dependency>
```
2、启动类添加注解`@EnableAspectJAutoProxy(proxyTargetClass = true)`

# libra-common-log 使用说明
在对应接口层类或者方法上添加注解和相关字段标识即可，比如`@Log(desc = "员工管理-分页查询", logType = LogTypeEnum.OPT_LOG, optBehavior = OptBehaviorEnum.SELECT)`
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

