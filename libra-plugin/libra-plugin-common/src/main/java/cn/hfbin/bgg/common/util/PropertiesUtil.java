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

package cn.hfbin.bgg.common.util;

import com.alibaba.spring.util.PropertySourcesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/2/01 11:10 下午
 * @description:
 */
public class PropertiesUtil {
    public static final Pattern PATTERN = Pattern.compile("-(\\w)");

    public static void enrichProperties(Properties properties, Environment environment, String prefix, boolean replaceExistedKey, boolean ignoreEmptyValue) {
        enrichProperties(properties, environment, PATTERN, prefix, replaceExistedKey, ignoreEmptyValue);
    }

    public static void enrichProperties(Properties properties, Environment environment, Pattern pattern, String prefix, boolean replaceExistedKey, boolean ignoreEmptyValue) {
        Map<String, Object> map = PropertySourcesUtils.getSubProperties((ConfigurableEnvironment) environment, prefix);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = resolveKey(pattern, entry.getKey());
            String value = String.valueOf(entry.getValue());

            addProperty(properties, key, value, replaceExistedKey, ignoreEmptyValue);
        }
    }

    public static void addProperty(Properties properties, String key, String value, boolean replaceExistedKey, boolean ignoreEmptyValue) {
        if (properties.containsKey(key)) {
            if (replaceExistedKey) {
                addProperty(properties, key, value, ignoreEmptyValue);
            }
        } else {
            addProperty(properties, key, value, ignoreEmptyValue);
        }
    }

    public static void addProperty(Properties properties, String key, String value, boolean ignoreEmptyValue) {
        if (StringUtils.isBlank(value)) {
            if (!ignoreEmptyValue) {
                properties.put(key, value);
            }
        } else {
            properties.put(key, value);
        }
    }

    public static String resolveKey(String key) {
        return resolveKey(PATTERN, key);
    }

    public static String resolveKey(Pattern pattern, String key) {
        Matcher matcher = pattern.matcher(key);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(stringBuffer);

        return stringBuffer.toString();
    }

}
