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

package cn.hfbin.common.log.enums;

import lombok.Getter;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2021/8/30 11:36 下午
 * @description: 操作行为
 */
public enum OptBehaviorEnum {

    NOT(0,"未知"),
    ADD(10,"新增"),
    UPDATE(20,"修改"),
    SELECT(30,"查询"),
    DELETE(40,"删除"),
    LOGIN(100,"登录"),
    LOGOUT(101,"登出"),
    ;
    @Getter
    private final int code;
    @Getter
    private final String msg;

    OptBehaviorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
