# 蓝绿灰度发布使用说明
> 蓝绿、灰度发布目前已抽成组件，与业务代码和其它组件没有任何关联，需要用时候非常简单只需要将对应组件依赖引入到对应需要蓝灰度发布的服务即可。

蓝绿灰度发布工程说明
```lua
libra-platform -- 父项目
│  │─libra-bgg -- 蓝绿灰度父级
│  │  ├─libra-bgg-common -- 蓝绿灰度公共封装
│  │  ├─libra-bgg-gateway -- 蓝绿灰度网关组件
│  │  ├─libra-bgg-service -- 蓝绿灰度非网关组件
```

**支持全链路混合实施蓝绿灰度发布**

网关 -> 服务全链路调用中，可以混合实施蓝绿灰度发布

- 网关上实施蓝绿发布，服务上实施灰度发布
- 网关上实施灰度发布，服务上实施蓝绿发布

服务 -> 服务全链路调用中，可以混合实施蓝绿灰度发布

- A服务上实施蓝绿发布，B服务上实施灰度发布
- B服务上实施灰度发布，A服务上实施蓝绿发布

**单节点混合实施蓝绿灰度发布**

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
          "expression":"#H['tenantCode'] == 'HDDC'", // expression匹配值是在请求头中获取，可根据请求头的属性进行匹对(使用spring spel)
          "routeKey":"route-1"
        },
        {
          "expression":"#H['tenantCode'] == 'AOLIN'",
          "routeKey":"route-2"
        }
      ],
      "basicCondition": "route-1" // 蓝绿发布兜底策列
    },
    "gray":{ // 灰度发布
      "conditionList":[ // 灰度策列
        {
          "expression":"#H['tenantCode'] == 'HDDC'",
          "routeKey":"route-1=40;route-2=60" // 【route-1=50;route-2=50】含义说明：route-1对应策列，40流量权重；route-1对应策列，60流量权重 （权重加起来100）
        },
        {
          "expression":"#H['tenantCode'] == 'AOLIN'",
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
          "expression":"#H['tenantCode'] == 'HDDC'", // expression匹配值是在请求头中获取，可根据请求头的属性进行匹对(使用spring spel)
          "routeKey":"route-1"
        },
        {
          "expression":"#H['tenantCode'] == 'AOLIN'",
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
          "expression":"#H['tenantCode'] == 'HDDC'",
          "routeKey":"route-1=40;route-2=60" // 【route-1=50;route-2=50】含义说明：route-1对应策列，40流量权重；route-1对应策列，60流量权重 （权重加起来100）
        },
        {
          "expression":"#H['tenantCode'] == 'AOLIN'",
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
    <artifactId>libra-bgg-gateway</artifactId>
    <version>1.0.0</version>
</dependency>
```
2、在`resources`下添加蓝绿、灰度发布规则rule.json

## 非网关接入蓝绿灰度发布

1、添加依赖
```xml
<dependency>
    <groupId>cn.hfbin</groupId>
    <artifactId>libra-bgg-service</artifactId>
    <version>1.0.0</version>
</dependency>
```
2、在`resources`下添加蓝绿、灰度发布规则rule.json