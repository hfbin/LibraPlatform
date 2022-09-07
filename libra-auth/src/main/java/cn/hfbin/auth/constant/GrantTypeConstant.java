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

package cn.hfbin.auth.constant;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2021/6/29 11:05 下午
 * @description:
 */
public interface GrantTypeConstant {

    // 手机验证码登录
    String MOBILE = "mobile";

    // 用户名+密码+验证码
    String PASSWORD = "password";

    // 微信openId登录方式
    String OPEN_ID = "openId";



}
