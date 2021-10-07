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

import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.common.core.exception.CommonExceptionCode;
import cn.hfbin.common.core.exception.LibraException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @Author: huangfubin
 * @Description: 远程调用返回处理
 * @Date: 2021/7/30
 */
@Slf4j
public class FeignResponseUtil {

    /**
     * 如果返回异常则此处默认处理返回为空
     * @param responseData
     * @param <T>
     * @return
     */
    public static <T> T get(ResponseData<T> responseData) {
        if(Objects.isNull(responseData)){
            return null;
        }
        if (responseData.isSuccess()) {
            return responseData.getData();
        }
        log.error("FeignResponse error, info:{}", responseData.getData());
        return null;
    }

    /**
     * 如果返回为异常则抛出具体异常
     * @param responseData
     * @param <T>
     * @return
     */
    public static <T> T getThrow(ResponseData<T> responseData) {
        if(Objects.isNull(responseData)){
            throw new LibraException(CommonExceptionCode.SYSTEM_502);
        }
        if (responseData.isSuccess()) {
            return responseData.getData();
        }else {
            throw new LibraException(responseData.getCode(), responseData.getMsg());
        }
    }
}
