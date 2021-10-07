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

package cn.hfbin.common.core.jwt.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: huangfubin
 * @Description: jwt需要放对应的数据
 * @Date: 2021/7/29
 */
@Data
@NoArgsConstructor
public class JwtUserInfo implements Serializable {

    private static final long serialVersionUID = -1L;

    private Long identityId;

    private Long accountId;

    private String username;

    private String tenantCode;

    private Long deptId;

    private String deptCode;

    private Integer dataScope;

    private Integer identityType;

}
