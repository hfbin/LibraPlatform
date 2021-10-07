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

package cn.hfbin.common.log.annotation;

import cn.hfbin.common.log.enums.LogTypeEnum;
import cn.hfbin.common.log.enums.OptBehaviorEnum;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * @author huangfubin
 * @date 2020/8/27
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Log {
    /**
     * 描述
     *
     * @return {String}
     */
    String desc() default "";

    /**
     * 日志类型
     *
     * @return {int}
     */
    LogTypeEnum logType() default LogTypeEnum.OPT_LOG;

    /**
     * 操作行为
     *
     * @return {int}
     */
    OptBehaviorEnum optBehavior() default OptBehaviorEnum.NOT;

    /**
     * 记录执行参数
     *
     * @return
     */
    boolean request() default true;

    /**
     * 记录返回参数
     *
     * @return
     */
    boolean response() default true;
}
