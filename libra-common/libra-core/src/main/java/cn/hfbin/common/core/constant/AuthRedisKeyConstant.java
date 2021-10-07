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

package cn.hfbin.common.core.constant;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2021/6/29 11:05 下午
 * @description: 认证服务redis key
 */
public interface AuthRedisKeyConstant {

    String AUTH = "AUTH" + SpecialCharacterPool.DOUBLE_COLON;

    // 用户菜单key [AUTH::MENU::clientCode::userId]
    String USER_MENU_KEY = AUTH + "MENU" + SpecialCharacterPool.DOUBLE_COLON;

    // 用户权限key [AUTH::INTERFACE::clientCode::userId]
    String USER_INTERFACE_KEY = AUTH + "INTERFACE" + SpecialCharacterPool.DOUBLE_COLON;

    // 用户信息key [AUTH::AUTH::clientCode::userId]
    String USER_INFO_KEY = AUTH + "INFO" + SpecialCharacterPool.DOUBLE_COLON;

}
