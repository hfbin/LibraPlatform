package cn.hfbin.bgg.common;

import cn.hfbin.bgg.common.entity.Rule;
import com.alibaba.fastjson.JSONObject;

/**
 * @Author: huangfubin
 * @Description: TestMain 类
 * @Date: 2021/10/13
 */
public class TestMain {

    public static void main(String[] args) {
        String json = "{\n" +
                "  \"strategy\":{\n" +
                "    \"routeKey\":\"route-1\"\n" +
                "  },\n" +
                "  \"strategyRelease\":{\n" +
                "    \"blueGreen\":{\n" +
                "      \"conditionList\":[\n" +
                "        {\n" +
                "          \"expression\":\"tenantCode=1\",\n" +
                "          \"routeKey\":\"route-1\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"expression\":\"tenantCode=2\",\n" +
                "          \"routeKey\":\"route-2\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"basicCondition\": \"route-1\"\n" +
                "    },\n" +
                "    \"gray\":{\n" +
                "      \"conditionList\":[\n" +
                "        {\n" +
                "          \"expression\":\"tenantCode=1\",\n" +
                "          \"routeKey\":\"route-1=50;route-2=50\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"expression\":\"tenantCode=1\",\n" +
                "          \"routeKey\":\"route-1=60;route-2=40\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"basicCondition\": \"route-1=100;route-2=0\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"routes\":{\n" +
                "    \"route-1\":\"{\\\"service-a\\\":\\\"1.0\\\", \\\"service-b\\\":\\\"1.0\\\", \\\"service-c\\\":\\\"3.0\\\"}\",\n" +
                "    \"route-2\":\"{\\\"service-a\\\":\\\"1.0\\\", \\\"service-b\\\":\\\"2.0\\\", \\\"service-c\\\":\\\"2.0\\\"}\"\n" +
                "  }\n" +
                "}";

        Rule rule = JSONObject.parseObject(json, Rule.class);

        System.out.println(rule.getRoutes().get(rule.getStrategyRelease().getBlueGreen().getBasicCondition()));
    }
}
