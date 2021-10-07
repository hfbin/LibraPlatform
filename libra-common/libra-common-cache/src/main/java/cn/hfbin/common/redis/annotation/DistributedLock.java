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

package cn.hfbin.common.redis.annotation;

import java.lang.annotation.*;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2021/8/5 11:50 下午
 * @description:
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DistributedLock {
    /**
     * key
     */
    String key() default "";

    /**
     * 锁超时时间【单位毫秒】
     */
    long expireSeconds() default 1000L;

    /**
     * 等待加锁超时时间,【单位毫秒】, -1->则表示一直等待
     */
    long waitTime() default 1000L;

    /**
     * 未取到锁时提示信息
     */
    String failMsg() default "获取锁失败，请稍后重试";
}
