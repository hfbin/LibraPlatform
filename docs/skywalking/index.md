# idea下添加skywalking agent

```aidl
-javaagent:skywalking-agent.jar -Dskywalking.agent.service_name=libra-base -Dskywalking.collector.backend_service=localhost:11800
```

# 排除相关监控接口
在\agent\config目录下创建apm-trace-ignore-plugin.config文件，内容如下

    trace.ignore_path=${SW_AGENT_TRACE_IGNORE_PATH:/base/actuator/**,/ucpm/actuator/**,/tr/actuator/**,/gen/actuator/**,/auth/actuator/**}

