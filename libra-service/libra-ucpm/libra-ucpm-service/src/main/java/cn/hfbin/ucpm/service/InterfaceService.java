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

import cn.hfbin.ucpm.entity.Interface;
import cn.hfbin.ucpm.params.MenuParams;
import cn.hfbin.ucpm.vo.TreeVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 接口表
 * @Date: 2021-06-16
 */
public interface InterfaceService {
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param anInterface
     * @return
     */
    Page<Interface> page(Integer pageNo, Integer pageSize, Interface anInterface);

    /**
     * 查询树结构
     * @param menuParams
     * @return
     */
    List<TreeVo> tree(MenuParams menuParams);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Interface getById(Long id);

    /**
     * 新增数据
     * @param anInterface
     * @return
     */
    int insert(Interface anInterface);

    /**
     * 更新数据(为null的字段不会更新)
     * @param anInterface
     * @return
     */
    int update(Interface anInterface);

    /**
     * 更新数据
     * @param anInterface
     * @return
     */
    int updateSomeColumnById(Interface anInterface);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 查询所有接口数据
     * @param anInterface
     * @return
     */
    List<Interface> list(Interface anInterface);

    /**
     * @description 根据菜单id查询接口权限编码code
     * @param menuIds
     * @author huangfubin
     * @date 2021/8/22
     * @return java.util.List<java.lang.String>
     */
    List<String> selectInterfaceCode(List<Long> menuIds);
}
