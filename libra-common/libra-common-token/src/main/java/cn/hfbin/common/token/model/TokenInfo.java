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

package cn.hfbin.common.token.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: huangfubin
 * @Description: JwtUtil工具生成token返回实体
 * @Date: 2021/7/29
 */
public class TokenInfo implements Serializable {
    private static final long serialVersionUID = -1L;
    /**
     * token
     */
    private String token;
    /**
     * 有效时间：单位：秒
     */
    private Long expire;
    /**
     * 到期时间
     */
    private LocalDateTime expiration;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
}
