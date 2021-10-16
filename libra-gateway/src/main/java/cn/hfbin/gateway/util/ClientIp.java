package cn.hfbin.gateway.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Objects;

/**
 * @Description
 * @Author HuangFuBin
 * @Date 2021-02-22
 */
public class ClientIp {
    public static String getIp(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        }
        return ip;
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "  \"strategy\":{\n" +
                "    \"routeKey\":\"route-1\"\n" +
                "  },\n" +
                "  \"strategyRelease\":{\n" +
                "    \"blueGreen\":{\n" +
                "      \"condition-1\":{\n" +
                "        \"expression\":\"#H['tenantCode'] == 'HDDC'\",\n" +
                "        \"routeKey\":\"route-1\"\n" +
                "      },\n" +
                "      \"condition-2\":{\n" +
                "        \"expression\":\"#H['tenantCode'] == 'HDDC'\",\n" +
                "        \"routeKey\":\"route-1\"\n" +
                "      },\n" +
                "      \"basicCondition\":{\n" +
                "        \"routeKey\":\"route-1\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"gray\":{\n" +
                "      \"condition-1\":{\n" +
                "        \"route\":\"route-1=50;route-2=50\"\n" +
                "      },\n" +
                "      \"condition-2\":{\n" +
                "        \"route\":\"route-1=50;route-2=50\"\n" +
                "      },\n" +
                "      \"basicCondition\":{\n" +
                "        \"route\":\"route-1=100;route-2=0\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"routes\":{\n" +
                "      \"route-1\":\"{\\\"service-a\\\":\\\"1.0\\\", \\\"service-b\\\":\\\"1.0\\\", \\\"service-c\\\":\\\"3.0\\\"}\",\n" +
                "      \"route-2\":\"{\\\"service-a\\\":\\\"1.0\\\", \\\"service-b\\\":\\\"2.0\\\", \\\"service-c\\\":\\\"2.0\\\"}\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        JSONObject toJSON = JSONObject.parseObject(json);
        System.out.println(toJSON.get("strategy"));
        System.out.println(toJSON.get("strategyRelease"));
    }
}
