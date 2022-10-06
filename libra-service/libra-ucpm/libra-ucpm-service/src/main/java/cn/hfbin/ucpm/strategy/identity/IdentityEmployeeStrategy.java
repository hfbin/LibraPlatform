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

import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.ucpm.entity.IdentityEmployee;
import cn.hfbin.ucpm.enums.UcPmExceptionCode;
import cn.hfbin.ucpm.service.IdentityEmployeeService;
import cn.hfbin.ucpm.vo.IdentityInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * @Author: huangfubin
 * @Description: 内部员工身份
 * @Date: 2021/9/30
 */
@Service
public class IdentityEmployeeStrategy implements IdentityStrategy {

    @Autowired
    private IdentityEmployeeService identityEmployeeService;

    @Override
    public IdentityInfoVo selectIdentityInfo(Long accountId) {
        IdentityEmployee identityEmployeeParams = new IdentityEmployee();
        identityEmployeeParams.setAccountId(accountId);
        IdentityEmployee identityEmployee = identityEmployeeService.select(identityEmployeeParams);
        Optional.ofNullable(identityEmployee).orElseThrow(() -> new LibraException(UcPmExceptionCode.IDENTITY_EMPLOYEE_IS_NULL));
        IdentityInfoVo identityInfoVo = new IdentityInfoVo();
        BeanUtils.copyProperties(identityEmployee, identityInfoVo);
        return identityInfoVo;
    }
}
