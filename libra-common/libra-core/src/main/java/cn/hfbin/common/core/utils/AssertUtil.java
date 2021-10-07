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

package cn.hfbin.common.core.utils;

import cn.hfbin.common.core.api.ServiceCode;
import cn.hfbin.common.core.exception.LibraException;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2021/9/25 12:41 上午
 * @description: Util class for checking arguments.
 */
public class AssertUtil {

    private AssertUtil(){}

    public static void notBlank(String string, ServiceCode serviceCode) {
        if (StrUtil.isEmpty(string)) {
            throw new LibraException(serviceCode);
        }
    }

    public static void notEmpty(Collection<?> collection, ServiceCode serviceCode) {
        if (CollectionUtil.isEmpty(collection)) {
            throw new LibraException(serviceCode);
        }
    }

    public static void notNull(Object object, ServiceCode serviceCode) {
        if (Objects.isNull(object)) {
            throw new LibraException(serviceCode);
        }
    }

    public static void isTrue(boolean value, ServiceCode serviceCode) {
        if (!value) {
            throw new LibraException(serviceCode);
        }
    }

}
