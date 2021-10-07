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

import cn.hfbin.common.core.enums.IdentityEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2021/8/8 3:20 下午
 * @description:
 */
@Slf4j
public class EnumUtil {

    /**
     * 判断某个枚举是否包某个code值
     * @param enumClass 枚举
     * @param code code
     * @return 存在返回true，否则返回false
     */
    public static boolean isInclude(Class enumClass, int code){
        List enumList = EnumUtils.getEnumList(enumClass);
        for (int i = 0;i<enumList.size(); i++){
            Object en = enumList.get(i);
            Class<?> enClass = en.getClass();
            try {
                Method method = enClass.getMethod("getCode"); // 需要与枚举类方法对应
                Object invoke = method.invoke(en, null);
                if(Integer.parseInt(invoke.toString()) == code) {
                    return true;
                }
            }catch (Exception e){
                e.printStackTrace();
                log.error("枚举执行getCode方法失败..." , e);
            }
        }
        return false;
    }

}
