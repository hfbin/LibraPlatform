# libra-common-springcloud 说明
此组件依赖了Nacos注册中心、Nacos配置中心、Openfeign服务调用、Loadbalancer负载均衡、Prometheus监控、Sentinel等，作为微服务的公共基础组件。

组件默认处理了Openfeign服务调用请求头的携带，Sentinel 流控、降级、参数热点、系统、授权 统一拦截处理

# libra-common-springcloud 接入说明
1、在对应服务pom.xml文件引入对应依赖
```xml
<dependency>
    <groupId>cn.hfbin</groupId>
    <artifactId>libra-common-springcloud</artifactId>
</dependency>
```
2、启动类添加注解`@EnableFeignClients`，如果依赖了调用其它服务则还需要执行扫描的包，比如`libra-ucpm-service`服务需要调用`libra-base-service`、`libra-tenant-service`服务则需要扫描对应服务包，如`@EnableFeignClients({TenantConstant.LIBRA_TENANT_PACKAGE, BaseConstant.LIBRA_BASE_PACKAGE})`

