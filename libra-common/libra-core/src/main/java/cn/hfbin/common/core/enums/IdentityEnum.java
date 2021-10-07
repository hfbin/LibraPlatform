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

package cn.hfbin.common.core.enums;

import lombok.Getter;

/**
 * @Author: huangfubin
 * @Description: BaseExceptionCode 类
 * @Date: 2021/7/7
 */
public enum IdentityEnum {

    EMPLOYEE(10, "内部员工身份"),
    DOMESTIC_CONSUMER(20, "普通用户身份");

    ;
    @Getter
    private int code;
    @Getter
    private String msg;

    IdentityEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     *
     * @description 根据code返回对应枚举
     * @param code
     * @author huangfubin
     * @date 2021/9/7
     * @return cn.hfbin.common.core.enums.IdentityEnum
     */
    public static IdentityEnum getEnumByCode(int code){
        for (IdentityEnum identityEnum:IdentityEnum.values()) {
            if(identityEnum.getCode() == code){
                return identityEnum;
            }
        }
        return null;
    }


}
