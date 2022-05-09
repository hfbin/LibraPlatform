# 蓝绿灰度发布使用说明
> 蓝绿、灰度发布目前已抽成组件，与业务代码和其它组件没有任何关联，需要用时候非常简单只需要将对应组件依赖引入到对应需要蓝灰度发布的服务即可。

蓝绿灰度发布工程说明
```lua
libra-platform -- 父项目
│  │─libra-plugin -- 扩展服务治理功能组件
│  │  ├─libra-plugin-common -- 公共
│  │  ├─libra-plugin-bgg -- 蓝绿灰度发布组件
│  │  ├─libra-plugin-nacos -- nacos二次封装组件
│  │  ├─libra-plugin-sentinel -- sentinel二次封装组件
│  │  ├─libra-plugin-gateway -- 网关组件
│  │  ├─libra-plugin-service -- 非网关组件
│  │  ├─libra-plugin-admin-service -- 暴露动态更新配置服务
```

**支持全链路混合实施蓝绿灰度发布**

网关 -> 服务全链路调用中，可以混合实施蓝绿灰度发布

- 网关上实施蓝绿发布，服务上实施灰度发布
- 网关上实施灰度发布，服务上实施蓝绿发布

服务 -> 服务全链路调用中，可以混合实施蓝绿灰度发布

- A服务上实施蓝绿发布，B服务上实施灰度发布
- B服务上实施灰度发布，A服务上实施蓝绿发布

**支持单节点混合实施蓝绿灰度发布**

网关或服务上可以同时配置蓝绿灰度发布策略，策略优先级：蓝绿 -> 灰度

## 蓝绿灰度发布配置说明rule.json
1、完整的混合蓝绿灰度发布配置
```json
{
  "strategy":{ // 兜底策列
    "routeKey":"route-1"
  },
  "strategyRelease":{
    "blueGreen":{ // 蓝绿发布
      "conditionList":[ // 蓝绿策列
        {
          "expression":"#H['Tenant-Code'] == 'HDDC'", // expression匹配值是在请求头中获取，可根据请求头的属性进行匹对(使用spring spel)
          "routeKey":"route-1"
        },
        {
          "expression":"#H['Tenant-Code'] == 'AOLIN'",
          "routeKey":"route-2"
        }
      ],
      "basicCondition": "route-1" // 蓝绿发布兜底策列
    },
    "gray":{ // 灰度发布
      "conditionList":[ // 灰度策列
        {
          "expression":"#H['Tenant-Code'] == 'HDDC'",
          "routeKey":"route-1=40;route-2=60" // 【route-1=50;route-2=50】含义说明：route-1对应策列，40流量权重；route-1对应策列，60流量权重 （权重加起来100）
        },
        {
          "expression":"#H['Tenant-Code'] == 'AOLIN'",
          "routeKey":"route-1=50;route-2=50"
        }
      ],
      "basicCondition": "route-1=100;route-2=0" // 灰度发布兜底策列
    }
  },
  "routes":{ // 版本策列
    "route-1":"{\"libra-tenant\":\"1.0\"}",
    "route-2":"{\"libra-tenant\":\"1.0\", \"libra-ucpm\":\"1.0\", \"libra-base\":\"1.0\"}"
  }
}
```
规则执行优先级：蓝绿 -> 灰度 -> 兜底，蓝绿灰度发布匹配条件支持多条件配置，expression支持spring sepl表达式规则。

2、蓝绿发布配置
```json
{
  "strategyRelease":{
    "blueGreen":{ // 蓝绿发布
      "conditionList":[ // 蓝绿策列
        {
          "expression":"#H['Tenant-Code'] == 'HDDC'", // expression匹配值是在请求头中获取，可根据请求头的属性进行匹对(使用spring spel)
          "routeKey":"route-1"
        },
        {
          "expression":"#H['Tenant-Code'] == 'AOLIN'",
          "routeKey":"route-2"
        }
      ],
      "basicCondition": "route-1" // 蓝绿发布兜底策列
    }
  },
  "routes":{ // 版本策列
    "route-1":"{\"libra-tenant\":\"1.0\"}",
    "route-2":"{\"libra-tenant\":\"1.0\", \"libra-ucpm\":\"1.0\", \"libra-base\":\"1.0\"}"
  }
}
```
3、灰度发布配置
```json
{
  "strategyRelease":{
    "gray":{ // 灰度发布
      "conditionList":[ // 灰度策列
        {
          "expression":"#H['Tenant-Code'] == 'HDDC'",
          "routeKey":"route-1=40;route-2=60" // 【route-1=50;route-2=50】含义说明：route-1对应策列，40流量权重；route-1对应策列，60流量权重 （权重加起来100）
        },
        {
          "expression":"#H['Tenant-Code'] == 'AOLIN'",
          "routeKey":"route-1=50;route-2=50"
        }
      ],
      "basicCondition": "route-1=100;route-2=0" // 灰度发布兜底策列
    }
  },
  "routes":{ // 版本策列
    "route-1":"{\"libra-tenant\":\"1.0\"}",
    "route-2":"{\"libra-tenant\":\"1.0\", \"libra-ucpm\":\"1.0\", \"libra-base\":\"1.0\"}"
  }
}
```


## 网关接入蓝绿灰度发布
1、添加依赖
```xml
<dependency>
    <groupId>cn.hfbin</groupId>
    <artifactId>libra-plugin-gateway</artifactId>
    <version>1.0.0</version>
</dependency>
```
2、在`resources`下添加蓝绿、灰度发布规则rule.json或者使用nacos动态配置（支持动态更新规则，nacos动态配置优先级大于`resources`下rule.json配置）

3、在`bootstrap.yml`添加如下配置，开启蓝绿灰度发布，默认是关闭的，如果在配置策略中包含的相关服务，必须在所包含的服务都设置为true，如果中间断层往后调用的服务链路将不起作用。
```yml
libra:
  # 如果nacos config配置没有配置文件则自动创建文件 默认开启
  nacos-config-init: true
  bgg:
    # 全蓝绿蓝绿灰度发布开启 默认开启
    enabled: true
    # 是否开启nacos config动态更新配置 默认开启
    remote-config:
      enabled: true
```
4、添加服务版本(必填)
```yml
spring:
  cloud:
    nacos:
      discovery:
        metadata:
          version: 1.0 # 版本
          service-type: gateway # 服务类型
```

说明：如果不需要蓝绿灰度发布引入了包务必将libra.bgg.enabled配置为false，蓝绿灰度发布是通过请求头进行传递相关策略信息，有一定耗时（具体损耗的时间待测试给出数据）。

## 非网关接入蓝绿灰度发布

1、添加依赖
```xml
<dependency>
    <groupId>cn.hfbin</groupId>
    <artifactId>libra-plugin-service</artifactId>
    <version>1.0.0</version>
</dependency>
```
2、在`resources`下添加蓝绿、灰度发布规则rule.json或者使用nacos动态配置（支持动态更新规则，nacos动态配置优先级大于`resources`下rule.json配置）

3、在`bootstrap.yml`添加如下配置，开启蓝绿灰度发布，默认是关闭的，如果在配置策略中包含的相关服务，必须在所包含的服务都设置为true，如果中间断层往后调用的服务链路将不起作用。
```yml
libra:
  # 如果nacos config配置没有配置文件则自动创建文件 默认开启
  nacos-config-init: true
  bgg:
    # 全蓝绿蓝绿灰度发布开启 默认开启
    enabled: true
    # 是否开启nacos config动态更新配置 默认开启
    remote-config:
      enabled: true
```
4、添加服务版本(必填)
```yml
spring:
  cloud:
    nacos:
      discovery:
        metadata:
          version: 1.0 # 版本
          service-type: service # 服务类型
```
说明：如果不需要蓝绿灰度发布引入了包务必将libra.bgg.enabled配置为false，蓝绿灰度发布是通过请求头进行传递相关策略信息，有一定耗时（具体损耗的时间待测试给出数据）。
