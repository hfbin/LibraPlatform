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

import cn.hfbin.ucpm.entity.Interface;
import cn.hfbin.ucpm.enums.UcPmExceptionCode;
import cn.hfbin.ucpm.mapper.InterfaceMapper;
import cn.hfbin.ucpm.params.MenuParams;
import cn.hfbin.ucpm.service.InterfaceService;
import cn.hfbin.ucpm.vo.TreeVo;
import cn.hfbin.common.core.exception.LibraException;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: huangfubin
 * @Description: 接口表
 * @Date: 2021-06-16
 */
@Service
public class InterfaceServiceImpl extends ServiceImpl<InterfaceMapper, Interface> implements InterfaceService {

    private final static String path = "%s(%s)";
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param anInterface
     * @return
     */
    @Override
    public Page<Interface> page(Integer pageNo, Integer pageSize, Interface anInterface) {
        Page<Interface> page = new Page(pageNo, pageSize);
        return baseMapper.selectPage(page, Wrappers.lambdaQuery(anInterface));
    }

    /**
     * 查询树结构
     * @param menuParams
     * @return
     */
    @Override
    public List<TreeVo> tree(MenuParams menuParams) {
        List<Interface> list = baseMapper.selectList(null);
        if(CollectionUtil.isEmpty(list)){
            return null;
        }
        List<TreeVo> treeVoList = new ArrayList<>();
        list.forEach(data -> {
            TreeVo treeVo = new TreeVo();
            treeVo.setParentId(data.getParentId());
            treeVo.setId(data.getId());
            if(StrUtil.isBlank(data.getInterfacePath())){
                treeVo.setName(data.getName());
            }else {
                treeVo.setName(String.format(path, data.getName(), data.getInterfacePath()));
            }
            treeVo.setChild(new ArrayList<>());
            treeVoList.add(treeVo);
        });
        return TreeVo.listToTree(treeVoList);
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Interface getById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        return baseMapper.deleteById(id);
    }

    /**
     * 查询所有接口数据
     * @param anInterface
     * @return
     */
    @Override
    public List<Interface> list(Interface anInterface) {
        LambdaQueryWrapper<Interface> wrapper = new LambdaQueryWrapper<>();
        if(!StrUtil.isBlank(anInterface.getName())){
            wrapper.like(Interface::getName, anInterface.getName());
        }
        if(!StrUtil.isBlank(anInterface.getClientCode())){
            wrapper.like(Interface::getClientCode, anInterface.getClientCode());
        }
        wrapper.orderByAsc(Interface::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    /**
     * 新增数据
     * @param anInterface
     * @return
     */
    @Override
    public int insert(Interface anInterface) {
        if(anInterface.getParentId() != 0L){
            Interface interfaceParent = baseMapper.selectById(anInterface.getParentId());
            Optional.ofNullable(interfaceParent).orElseThrow(() -> new LibraException(UcPmExceptionCode.SELECT_PARENT_ID_ERROR));
            anInterface.setClientCode(interfaceParent.getClientCode());
        }
        return baseMapper.insert(anInterface);
    }

    /**
     * 更新数据(为null的字段不会更新)
     * @param anInterface
     * @return
     */
    @Override
    public int update(Interface anInterface) {
        if(anInterface.getParentId() != 0L){
            Interface interfaceParent = baseMapper.selectById(anInterface.getParentId());
            Optional.ofNullable(interfaceParent).orElseThrow(() -> new LibraException(UcPmExceptionCode.SELECT_PARENT_ID_ERROR));
            anInterface.setClientCode(interfaceParent.getClientCode());
        }
        return baseMapper.updateById(anInterface);
    }

    /**
     * 更新数据(为null的字段会更新)
     * @param anInterface
     * @return
     */
    @Override
    public int updateSomeColumnById(Interface anInterface) {
        return baseMapper.alwaysUpdateSomeColumnById(anInterface);
    }

    /**
     *  根据菜单id查询接口权限编码code
     * @description
     * @param menuIds
     * @author huangfubin
     * @date 2021/8/22
     * @return java.util.List<java.lang.String>
     */
    @Override
    public List<String> selectInterfaceCode(List<Long> menuIds){
        if(CollectionUtil.isEmpty(menuIds)){
            return null;
        }
        return baseMapper.selectInterfaceCode(menuIds);
    }
}
