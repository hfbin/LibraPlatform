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

package cn.hfbin.ucpm.mapper;


import cn.hfbin.ucpm.entity.IdentityEmployee;
import cn.hfbin.ucpm.params.EmployeeQueryParams;
import cn.hfbin.ucpm.vo.IdentityInfoVo;
import cn.hfbin.common.database.SuperMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: huangfubin
 * @Description: 员工身份信息表
 * @Date: 2021-06-16
 */
@Mapper
public interface IdentityEmployeeMapper extends SuperMapper<IdentityEmployee> {

    /**
     * 查询员工列表
     * @param page
     * @param employeeQueryParams
     * @return
     */
    Page<IdentityInfoVo> selectPageList(@Param("page") Page<IdentityInfoVo> page, @Param("params") EmployeeQueryParams employeeQueryParams);

}
