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

package cn.hfbin.gateway.enums;

import cn.hfbin.common.core.api.ServiceCode;

/**
 * @Author: huangfubin
 * @Description: BaseExceptionCode 类
 * @Date: 2021/7/7
 */
public enum GatewayExceptionCode implements ServiceCode {

    TOKEN_INVALID(5020,"token失效"),
    TOKEN_EMPTY(5021,"token不能为空"),


    ;
    private int code;
    private String msg;

    GatewayExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
