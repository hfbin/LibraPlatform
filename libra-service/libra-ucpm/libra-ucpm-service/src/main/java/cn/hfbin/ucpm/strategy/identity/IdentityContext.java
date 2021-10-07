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

package cn.hfbin.ucpm.strategy.identity;

import cn.hfbin.common.core.enums.IdentityEnum;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.ucpm.enums.UcPmExceptionCode;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: huangfubin
 * @Description: IdentityContext 类
 * @Date: 2021/9/30
 */
@Component
public class IdentityContext {
    private final Map<String, IdentityStrategy> identityMap = new ConcurrentHashMap<>();

    /**
     *
     * @param identityMap
     * @author huangfubin
     * @date 2021/7/29
     * @return
     */
    public IdentityContext(Map<String, IdentityStrategy> identityMap) {
        identityMap.forEach(this.identityMap::put);
    }

    /**
     *
     * @description 根据 identityEnum 获取对应实例
     * @param identityEnum
     * @author huangfubin
     * @return IdentityStrategyr
     */
    public IdentityStrategy getIdentityStrategy(IdentityEnum identityEnum) {
        IdentityStrategy tokenGranter = identityMap.get(identityEnum.name());
        Optional.ofNullable(tokenGranter).orElseThrow(()->new LibraException(UcPmExceptionCode.IDENTITY_INEXISTENCE));
        return tokenGranter;
    }
}
