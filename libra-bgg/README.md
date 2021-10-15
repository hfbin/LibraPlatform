# 蓝绿、灰度发布接入说明
> 蓝绿、灰度发布目前已抽成组件，与业务代码没有任何关系，需要用时候非常简单只需要将对应组件依赖引入对应需要灰度发布的服务即可。
## 蓝绿、灰度发布规则配置说明rule.json
```json
{
  "strategy":{ // 兜底策列
    "routeKey":"route-1"
  },
  "strategyRelease":{
    "blueGreen":{ // 蓝绿发布
      "conditionList":[ // 蓝绿策列
        {
          "expression":"tenantCode=1", // expression匹配值是在请求头中获取，可根据请求的属性进行匹对
          "routeKey":"route-1"
        },
        {
          "expression":"tenantCode=1",
          "routeKey":"route-1"
        }
      ],
      "basicCondition": "route-1" // 蓝绿发布兜底策列
    },
    "gray":{ // 灰度发布
      "conditionList":[ // 灰度策列
        {
          "expression":"tenantCode=1",
          "routeKey":"route-1=40;route-2=60" // 【route-1=50;route-2=50】含义说明：route-1对应策列，40流量权重；route-1对应策列，60流量权重 （权重加起来100）
        },
        {
          "expression":"tenantCode=1",
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

## 基于网关接入蓝绿、灰度发布
1、添加依赖
```xml
<dependency>
    <groupId>cn.hfbin</groupId>
    <artifactId>libra-bgg-gateway</artifactId>
    <version>1.0.0</version>
</dependency>
```
2、在`resources`下添加蓝绿、灰度发布规则rule.json

## 基于服务接入蓝绿、灰度发布

1、添加依赖
```xml
<dependency>
    <groupId>cn.hfbin</groupId>
    <artifactId>libra-bgg-service</artifactId>
    <version>1.0.0</version>
</dependency>
```
2、在`resources`下添加蓝绿、灰度发布规则rule.json