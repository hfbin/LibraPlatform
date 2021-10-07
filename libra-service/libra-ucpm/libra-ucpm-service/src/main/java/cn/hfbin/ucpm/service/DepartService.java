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

package cn.hfbin.ucpm.service;

import cn.hfbin.ucpm.entity.Depart;
import cn.hfbin.ucpm.params.DepartParams;
import cn.hfbin.ucpm.vo.DepartTreeVo;
import cn.hfbin.ucpm.vo.DepartVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 组织机构表
 * @Date: 2021-06-16
 */
public interface DepartService {
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param baseDepart
     * @return
     */
    Page<Depart> page(Integer pageNo, Integer pageSize, DepartParams baseDepart);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    DepartVo selectById(Long id);
    /**
     * 根据id查询
     * @param id
     * @return
     */
    Depart getById(Long id);

    /**
     * 新增数据
     * @param depart
     * @return
     */
    int insert(Depart depart);

    /**
     * [初始化租户使用] 创建租户默认部门，不可删除，仅限创建根部门使用
     * @param depart
     * @return
     */
    int insertIgnoreTr(Depart depart);

    /**
     * 更新数据(为null的字段不会更新)
     * @param depart
     * @return
     */
    int update(Depart depart);

    /**
     * 更新数据
     * @param depart
     * @return
     */
    int updateSomeColumnById(Depart depart);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     *
     * @description 部门列表（树结构）
     * @param departParams
     * @author huangfubin
     * @date 2021/7/6
     * @return java.util.List<DepartTreeVo>
     */
    List<DepartTreeVo> treeList(DepartParams departParams);

    /**
     *
     * @description 查询一级部门
     * @param departParams
     * @author huangfubin
     * @date 2021/7/6
     * @return java.util.List<DepartVo>
     */
    List<DepartVo> firstTreeList(DepartParams departParams);

    /**
     * 查询部门列表（可扩展）
     * @param departParams
     * @return
     */
    List<Depart> selectList(DepartParams departParams);
}
