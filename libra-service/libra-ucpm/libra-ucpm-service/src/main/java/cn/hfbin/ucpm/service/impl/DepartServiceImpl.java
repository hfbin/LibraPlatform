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

package cn.hfbin.ucpm.service.impl;


import cn.hfbin.ucpm.entity.Depart;
import cn.hfbin.ucpm.entity.IdentityDepartRef;
import cn.hfbin.ucpm.enums.UcPmExceptionCode;
import cn.hfbin.ucpm.mapper.DepartMapper;
import cn.hfbin.ucpm.mapper.IdentityDepartRefMapper;
import cn.hfbin.ucpm.params.DepartParams;
import cn.hfbin.ucpm.service.DepartService;
import cn.hfbin.ucpm.vo.DepartTreeVo;
import cn.hfbin.ucpm.vo.DepartVo;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.common.core.utils.DeptCodeUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: huangfubin
 * @Description: 组织机构表
 * @Date: 2021-06-16
 */
@Service
public class DepartServiceImpl extends ServiceImpl<DepartMapper, Depart> implements DepartService {

    @Autowired
    IdentityDepartRefMapper identityDepartRefMapper;

    /**
     * 查询部门树结构
     * @param departParams
     * @return
     */
    @Override
    public List<DepartTreeVo> treeList(DepartParams departParams) {
        LambdaQueryWrapper<Depart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Depart::getSortNo);
        List<Depart> departList = baseMapper.selectList(queryWrapper);
        if(CollectionUtil.isEmpty(departList)){
            return null;
        }
        List<DepartTreeVo> departTreeVoList = new ArrayList<>();
        departList.forEach(data -> {
            DepartTreeVo departTreeVo = new DepartTreeVo();
            BeanUtils.copyProperties(data, departTreeVo);
            departTreeVo.setLabel(data.getDeptName());
            departTreeVoList.add(departTreeVo);
        });
        return DepartTreeVo.listToTree(departTreeVoList);
    }

    /**
     * 根据父id查询对应子部门（配合前端组件懒加载，避免数据过多卡死）
     * @param departParams
     * @return
     */
    @Override
    public List<DepartVo> firstTreeList(DepartParams departParams) {
        LambdaQueryWrapper<Depart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Depart::getSortNo);
        if(departParams.getId() != null){
            queryWrapper.eq(Depart::getParentId, departParams.getId());
        }else {
            queryWrapper.eq(Depart::getParentId, 0);
        }
        List<Depart> departList = baseMapper.selectList(queryWrapper);
        if(CollectionUtil.isEmpty(departList)){
            return null;
        }
        List<DepartVo> departVoList = new ArrayList<>();
        departList.forEach(data -> {
            DepartTreeVo departTreeVo = new DepartTreeVo();
            BeanUtils.copyProperties(data, departTreeVo);
            departVoList.add(departTreeVo);
        });
        return departVoList;
    }

    /**
     * 查询部门列表（可扩展）
     * @param departParams
     * @return
     */
    @Override
    public List<Depart> selectList(DepartParams departParams) {
        LambdaQueryWrapper<Depart> queryWrapper = new LambdaQueryWrapper<>();
        if(CollectionUtil.isNotEmpty(departParams.getIds())){
            queryWrapper.in(Depart::getId, departParams.getIds());
        }
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param baseDepart
     * @return
     */
    @Override
    public Page<Depart> page(Integer pageNo, Integer pageSize, DepartParams baseDepart) {
        Page<Depart> page = new Page(pageNo, pageSize);
        LambdaQueryWrapper<Depart> pageList = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(baseDepart.getKeyword())){
            pageList.like(Depart::getDeptName, baseDepart.getKeyword());
        }
        pageList.orderByDesc(Depart::getUpdateTime);
        pageList.orderByDesc(Depart::getCreateTime);

        Page<Depart> baseDepartPage = baseMapper.selectPage(page, pageList);
        // 处理部门链路
        baseDepartPage.getRecords().forEach(data -> {
            if(StrUtil.isNotBlank(data.getParentIds())){
                String[] split = data.getParentIds().split(",");
                List<String> ids = Arrays.asList(split);
                LambdaQueryWrapper<Depart> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.in(Depart::getId, ids);
                List<Depart> departs = baseMapper.selectList(queryWrapper);
                StringBuilder idsName = new StringBuilder();
                for (int i = 0; i < departs.size(); i++) {
                    idsName.append(departs.get(i).getDeptName());
                    if (i != departs.size() - 1) {
                        idsName.append("-");
                    }
                }
                data.setParentIds(idsName.toString());
            }else {
                data.setParentIds("根节点");
            }
        });
        return baseDepartPage;
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Depart getById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public DepartVo selectById(Long id) {
        Depart baseDepart = baseMapper.selectById(id);
        Optional.ofNullable(baseDepart).orElseThrow(() -> new LibraException(UcPmExceptionCode.DEPT_IS_NULL));
        DepartVo departVo = new DepartVo();
        BeanUtils.copyProperties(baseDepart, departVo);
        if(baseDepart.getParentId() != 0L){
            Depart depart = baseMapper.selectById(baseDepart.getParentId());
            departVo.setParentDeptName(depart.getDeptName());
        }
        return departVo;
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        Depart depart = baseMapper.selectById(id);
        Optional.ofNullable(depart).orElseThrow(() -> new LibraException(UcPmExceptionCode.DEPT_IS_NULL));
        // 系统默认数据无法删除
        if(depart.getSystemDefault() == 1){
            throw new LibraException(UcPmExceptionCode.SYSTEM_DEFAULT_DELETE);
        }
        // 查询此部门是否存在关联
        LambdaQueryWrapper<IdentityDepartRef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(IdentityDepartRef::getDeptId, id);
        Integer count = identityDepartRefMapper.selectCount(queryWrapper);
        if(count > 0){
            throw new LibraException(UcPmExceptionCode.DEPT_EMP_EXIST);
        }
        return baseMapper.deleteById(id);
    }

    /**
     * 新增数据
     * @param depart
     * @return
     */
    @Override
    public int insert(Depart depart) {
        if(depart.getParentId() != null && depart.getParentId() != 0L){
            Depart checkDept = baseMapper.selectById(depart.getParentId());
            Optional.ofNullable(checkDept).orElseThrow(() -> new LibraException(UcPmExceptionCode.SELECT_PARENT_ID_ERROR));
            depart.setParentIds(this.selectParentIds(checkDept));
        }else {
            depart.setParentId(0L);
        }
        depart.setDeptCode(this.genDeptCode(depart.getParentId()));
        return baseMapper.insert(depart);
    }

    /**
     * 查找父id链路
     * @param depart
     * @return
     */
    private String selectParentIds(Depart depart) {
        boolean flag = true;
        Long parentId = depart.getParentId();
        List<Long> ids = new ArrayList<>();
        ids.add(depart.getId());
        if(parentId == 0L){
            return ids.get(0).toString();
        }
        while (flag){
            if(parentId == 0L){
                flag = false;
                break;
            }
            Depart checkDept = baseMapper.selectById(parentId);
            ids.add(checkDept.getId());
            parentId = checkDept.getParentId();
        }
        // 反序
        Collections.reverse(ids);
        StringBuilder lastIds = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if(i != 0){
                lastIds.append(",");
            }
            lastIds.append(ids.get(i));
        }
        return lastIds.toString();
    }

    /**
     * [不拼接租户条件] 创建租户默认部门，不可删除，仅限创建根部门使用
     * @param depart
     * @return
     */
    @Override
    public int insertIgnoreTr(Depart depart){
        depart.setParentId(0L);
        depart.setDeptCode(DeptCodeUtil.genCode(null));
        depart.setSystemDefault(1);
        depart.setDeptType(1);
        return baseMapper.insert(depart);
    }

    /**
     * 更新数据(为null的字段不会更新)，修改时候ParentIds、DeptCode需要重新更新
     * @param depart
     * @return
     */
    @Override
    public int update(Depart depart) {
        Depart selectDept = baseMapper.selectById(depart.getId());
        Optional.ofNullable(selectDept).orElseThrow(() -> new LibraException(UcPmExceptionCode.SELECT_IS_NULL));
        // 系统默认数据无法修改
        if(selectDept.getSystemDefault() == 1){
            throw new LibraException(UcPmExceptionCode.SYSTEM_DEFAULT_UPDATE);
        }
        // 父部门不一致则更新链路
        if(!selectDept.getParentId().equals(depart.getParentId())){
            if(depart.getParentId() != 0L){
                Depart checkDept = baseMapper.selectById(depart.getParentId());
                Optional.ofNullable(checkDept).orElseThrow(() -> new LibraException(UcPmExceptionCode.SELECT_PARENT_ID_ERROR));
                depart.setParentIds(this.selectParentIds(checkDept));
            }else {
                depart.setParentIds("");
            }
            depart.setDeptCode(this.genDeptCode(depart.getParentId()));
        }
        return baseMapper.updateById(depart);
    }

    /**
     * 更新数据(为null的字段会更新)
     * @param depart
     * @return
     */
    @Override
    public int updateSomeColumnById(Depart depart) {
        return baseMapper.alwaysUpdateSomeColumnById(depart);
    }

    /**
     * 自动生成有规则的部门编码
     * @param parentId
     * @return
     */
    public String genDeptCode(Long parentId) {
        // 定义新编码字符串
        String newOrgCode = "";
        //如果是最高级,则查询出同级的org_code, 调用工具类生成编码并返回
        if (parentId == null || parentId == 0L) {
            // 线判断数据库中的表是否为空,空则直接返回初始编码
            LambdaQueryWrapper<Depart> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Depart::getParentId, 0).or().isNull(Depart::getParentId);
            queryWrapper.orderByDesc(Depart::getDeptCode);
            List<Depart> departList = baseMapper.selectList(queryWrapper);
            if (CollectionUtil.isEmpty(departList)) {
                return DeptCodeUtil.genCode(null);
            } else {
                String deptCode = departList.get(0).getDeptCode();
                newOrgCode = DeptCodeUtil.genCode(deptCode);
            }
        //反之则查询出所有同级的部门,获取结果后有两种情况,有同级和没有同级
        } else {
            LambdaQueryWrapper<Depart> query = new LambdaQueryWrapper<>();
            // 封装查询同级的条件
            query.eq(Depart::getParentId, parentId);
            // 降序排序
            query.orderByDesc(Depart::getDeptCode);
            // 查询出同级部门的集合
            List<Depart> parentList = baseMapper.selectList(query);
            // 查询出父级部门
            Depart depart = baseMapper.selectById(parentId);
            // 获取父级部门的Code
            String parentCode = depart.getDeptCode();
            // 根据父级部门类型算出当前部门的类型
            // 处理同级部门为null的情况
            if (CollectionUtil.isEmpty(parentList)) {
                // 直接生成当前的部门编码并返回
                newOrgCode = DeptCodeUtil.genSubCode(parentCode, null);
            //处理有同级部门的情况
            } else {
                // 获取同级部门的编码,利用工具类
                String subCode = parentList.get(0).getDeptCode();
                // 返回生成的当前部门编码
                newOrgCode = DeptCodeUtil.genSubCode(parentCode, subCode);
            }
        }
        // 返回最终封装了部门编码和部门类型的数组
        return newOrgCode;
    }
}
