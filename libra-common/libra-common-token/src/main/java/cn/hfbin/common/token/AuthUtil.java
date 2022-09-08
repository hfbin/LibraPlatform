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

package cn.hfbin.common.token;

import cn.hfbin.common.token.constant.ConfigValueConstant;
import cn.hfbin.common.token.constant.JwtKeyConstant;
import cn.hfbin.common.token.model.AuthUserInfo;
import cn.hfbin.common.token.model.JwtUserInfo;
import cn.hfbin.common.token.model.TokenInfo;
import cn.hutool.core.convert.Convert;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: huangfubin
 * @Description: TokenUtil 类
 * @Date: 2021/7/30
 */
public class AuthUtil {

    /**
     * 过期时间 12h 单位：s
     * 默认：12 * 60 * 60s = 43200
     */
    @Value("${" + ConfigValueConstant.JWT_EXPIRE + ":43200}")
    private Long expire;

    /**
     * 刷新token的过期时间12h 单位：s
     * 默认：12 * 60 * 60s = 43200
     */
    @Value("${" + ConfigValueConstant.JWT_REFRESH_EXPIRE + ":43200}")
    private Long refreshExpire;

    /**
     * 设置解析token时，允许的误差 单位：s
     * 默认：60s
     */
    @Value("${" + ConfigValueConstant.JWT_ALLOWABLE_ERROR_TIME + ":60}")
    private Long allowableErrorTime;

    /**
     * 创建 Token
     *
     * @param userInfo 用户信息
     * @return token
     */
    public AuthUserInfo createToken(JwtUserInfo userInfo) {
        //设置jwt参数
        Map<String, String> param = new HashMap<>(8);
        param.put(JwtKeyConstant.USER_ID, Convert.toStr(userInfo.getIdentityId(), "0"));
        param.put(JwtKeyConstant.ACCOUNT_ID, Convert.toStr(userInfo.getAccountId(), "0"));
        param.put(JwtKeyConstant.TENANT_CODE, userInfo.getTenantCode());
        param.put(JwtKeyConstant.DEPT_ID, Convert.toStr(userInfo.getDeptId(), "0"));
        param.put(JwtKeyConstant.DEPT_CODE, userInfo.getDeptCode());
        param.put(JwtKeyConstant.DATA_SCOPE, Convert.toStr(userInfo.getDataScope()));
        param.put(JwtKeyConstant.USERNAME, Convert.toStr(userInfo.getUsername()));
        param.put(JwtKeyConstant.IDENTITY_TYPE, Convert.toStr(userInfo.getIdentityType()));

        TokenInfo token = JwtUtil.generateJwt(param, expire);

        AuthUserInfo authInfo = new AuthUserInfo();
        authInfo.setIdentityId(userInfo.getIdentityId());
        authInfo.setToken(token.getToken());
        authInfo.setExpire(token.getExpire());
        authInfo.setExpiration(token.getExpiration());
        authInfo.setRefreshToken(createRefreshToken(userInfo).getToken());
        return authInfo;
    }

    /**
     * 创建 Refresh Token
     *
     * @param userInfo 用户信息
     * @return Token
     */
    private TokenInfo createRefreshToken(JwtUserInfo userInfo) {
        Map<String, String> param = new HashMap<>(1);
        param.put(JwtKeyConstant.USER_ID, Convert.toStr(userInfo.getIdentityId(), "0"));
        return JwtUtil.generateJwt(param, refreshExpire);
    }

    /**
     * 解析 Token
     *
     * @param token token
     * @return JwtUserInfo
     */
    public JwtUserInfo getAuthInfo(String token) {
        Claims claims = JwtUtil.parseJwt(token, allowableErrorTime);
        Long userId = Convert.toLong(claims.get(JwtKeyConstant.USER_ID));
        Long accountId = Convert.toLong(claims.get(JwtKeyConstant.ACCOUNT_ID));
        Long deptId = Convert.toLong(claims.get(JwtKeyConstant.DEPT_ID));
        String deptCode = Convert.toStr(claims.get(JwtKeyConstant.DEPT_CODE));
        String tenantCode = Convert.toStr(claims.get(JwtKeyConstant.TENANT_CODE));
        Integer dataScope = Convert.toInt(claims.get(JwtKeyConstant.DATA_SCOPE));
        String username = Convert.toStr(claims.get(JwtKeyConstant.USERNAME));
        Integer identityType = Convert.toInt(claims.get(JwtKeyConstant.IDENTITY_TYPE));

        JwtUserInfo jwtUserInfo = new JwtUserInfo();
        jwtUserInfo.setAccountId(accountId);
        jwtUserInfo.setIdentityType(identityType);
        jwtUserInfo.setIdentityId(userId);
        jwtUserInfo.setDeptCode(deptCode);
        jwtUserInfo.setDeptId(deptId);
        jwtUserInfo.setDataScope(dataScope);
        jwtUserInfo.setTenantCode(tenantCode);
        jwtUserInfo.setUsername(username);

        return jwtUserInfo;
    }

    /**
     * 解析刷新 Refresh Token
     *
     * @param token
     * @return JwtUserInfo
     */
    public JwtUserInfo parseRefreshToken(String token) {
        Claims claims = JwtUtil.parseJwt(token, allowableErrorTime);
        Long userId = Convert.toLong(claims.get(JwtKeyConstant.USER_ID));
        JwtUserInfo jwtUserInfo = new JwtUserInfo();
        jwtUserInfo.setIdentityId(userId);
        return jwtUserInfo;
    }

}
