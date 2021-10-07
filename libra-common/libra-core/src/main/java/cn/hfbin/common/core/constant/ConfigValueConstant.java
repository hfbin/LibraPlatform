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

package cn.hfbin.common.core.constant;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2021/6/29 11:05 下午
 * @description:
 */
public interface ConfigValueConstant {

    String SPRING_APPLICATION_NAME = "spring.application.name";
    String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    // ------------gateway------------------
    String GATEWAY_URI_FILTER = "libra.gateway.uri.filter";

    // ------------thread------------------
    String THREAD_POOL_CORE_SIZE = "libra.thread.pool.coreSize";
    String THREAD_POOL_MAX_SIZE = "libra.thread.pool.maxSize";
    String THREAD_POOL_KEEPALIVE = "libra.thread.pool.keepalive";
    String THREAD_POOL_BLOCK_QUEUE_SIZE = "libra.thread.pool.blockQueueSize";


    // ------------JWT------------------
    String JWT_EXPIRE ="libra.jwt.expire";
    String JWT_REFRESH_EXPIRE ="libra.jwt.refreshExpire";
    String JWT_ALLOWABLE_ERROR_TIME ="libra.jwt.allowableErrorTime";


    // ------------LOG------------------
    String LOG_ENABLED ="libra.log.enabled";
    String LOG_DB ="libra.log.db";


    // ------------mybatis plus------------------
    String MYBATIS_PLUS_IGNORE_TABLE ="libra.mybatisPlus.ignoreTable";
    String MYBATIS_PLUS_OPEN_TENANT ="libra.mybatisPlus.openTenant";


    // ------------security------------------
    String SECURITY_ENABLED = "libra.security.enabled";



}
