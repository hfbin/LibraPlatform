/*
 *    Copyright [2021] [LibraPlatform of copyright huangfubin]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package cn.hfbin.common.core.config;

import cn.hfbin.common.core.constant.ConfigValueConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: huangfubin
 * @Description: SpringAsyncThreadConfig 类
 * @Date: 2021/9/15
 */
@EnableAsync
@Configuration
public class SpringAsyncThreadConfig {

    @Value("${" + ConfigValueConstant.THREAD_POOL_CORE_SIZE + ":100}")
    private Integer coreSize;
    @Value("${" + ConfigValueConstant.THREAD_POOL_MAX_SIZE + ":500}")
    private Integer maxSize;
    @Value("${" + ConfigValueConstant.THREAD_POOL_KEEPALIVE + ":60}")
    private Integer keepalive;
    @Value("${" + ConfigValueConstant.THREAD_POOL_BLOCK_QUEUE_SIZE + ":1000}")
    private Integer blockQueueSize;

    private final static String threadNamePrefix = "SpringAsyncTaskExecutor-";

    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        // 通过Runtime方法来获取当前服务器cpu内核，根据cpu内核来创建核心线程数和最大线程数
        int threadCount = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadCount > coreSize ? coreSize : threadCount);
        executor.setMaxPoolSize(threadCount > maxSize ? maxSize : threadCount);
        executor.setQueueCapacity(blockQueueSize);
        executor.setKeepAliveSeconds(keepalive);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
