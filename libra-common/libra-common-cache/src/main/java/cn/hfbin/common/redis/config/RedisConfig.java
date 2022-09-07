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

package cn.hfbin.common.redis.config;

import cn.hfbin.common.redis.util.RedisUtil;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2021/8/5 10:57 下午
 * @description:
 */
@SuppressWarnings("all")
@Configuration
public class RedisConfig {
    @Bean
    public RedisSerializer<String> redisKeySerializer() {
        return new StringRedisSerializer();
    }

    /**
     *
     * @description 使用FastJson
     * @param
     * @author huangfubin
     * @date 2021/8/5
     * @return org.springframework.data.redis.serializer.RedisSerializer<java.lang.Object>
     */
    @Bean
    public RedisSerializer<Object> redisValueSerializer() {
        return new GenericFastJsonRedisSerializer();
    }

    /**
     *
     * @description RedisTemplate配置
     * @param factory
     * @param redisKeySerializer
     * @param redisValueSerializer
     * @author huangfubin
     * @date 2021/8/5
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.String,java.lang.Object>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory, RedisSerializer<String> redisKeySerializer, RedisSerializer<Object> redisValueSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //设置Key的序列化采用StringRedisSerializer
        redisTemplate.setKeySerializer(redisKeySerializer);
        redisTemplate.setHashKeySerializer(redisKeySerializer);
        //设置值的序列化
        redisTemplate.setValueSerializer(redisValueSerializer);
        redisTemplate.setHashValueSerializer(redisValueSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisUtil redisUtil(){
        return new RedisUtil();
    }
}
