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

import cn.hfbin.ucpm.entity.Client;
import cn.hfbin.ucpm.entity.Menu;
import cn.hfbin.ucpm.enums.UcPmExceptionCode;
import cn.hfbin.ucpm.mapper.ClientMapper;
import cn.hfbin.ucpm.service.ClientService;
import cn.hfbin.ucpm.service.MenuService;
import cn.hfbin.common.core.exception.LibraException;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: huangfubin
 * @Description: 客户端管理
 * @Date: 2021-08-27
 */
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements ClientService {

    @Autowired
    private MenuService menuService;

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param client
     * @return
     */
    @Override
    public Page<Client> page(Integer pageNo, Integer pageSize, Client client) {
        Page<Client> page = new Page(pageNo, pageSize);
        return baseMapper.selectPage(page, Wrappers.lambdaQuery(client));
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Client getById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        Client select = baseMapper.selectById(id);
        Optional.ofNullable(select).orElseThrow(()-> new LibraException(UcPmExceptionCode.SELECT_IS_NULL));
        Menu menu = new Menu();
        menu.setClientCode(select.getClientCode());
        List<Menu> menuList = menuService.list(menu);
        if(CollectionUtil.isNotEmpty(menuList)){
            throw new LibraException(UcPmExceptionCode.CLIENT_CANNOT_BE_DELETE);
        }
        return baseMapper.deleteById(id);
    }

    /**
     *
     * @description 查询列表
     * @param client
     * @author huangfubin
     * @date 2021/8/27
     * @return java.util.List<BaseClient>
     */
    @Override
    public List<Client> list(Client client) {
        return baseMapper.selectList(Wrappers.lambdaQuery(client));
    }

    /**
     * 根据客户端编码查询详细信息
     * @param clientCode
     * @return
     */
    @Override
    public Client selectByCode(String clientCode) {
        return baseMapper.selectOne(Wrappers.<Client>lambdaQuery().eq(Client::getClientCode, clientCode));
    }

    /**
     * 新增数据
     * @param client
     * @return
     */
    @Override
    public int insert(Client client) {
        return baseMapper.insert(client);
    }

    /**
     * 更新数据(为null的字段不会更新)
     * @param client
     * @return
     */
    @Override
    public int update(Client client) {
        Client select = baseMapper.selectById(client.getId());
        Optional.ofNullable(select).orElseThrow(()-> new LibraException(UcPmExceptionCode.SELECT_IS_NULL));
        if(!select.getClientCode().equals(client.getClientCode())){
            throw new LibraException(UcPmExceptionCode.CLIENT_CODE_CANNOT_BE_MODIFIED);
        }
        return baseMapper.updateById(client);
    }

    /**
     * 更新数据(为null的字段会更新)
     * @param client
     * @return
     */
    @Override
    public int updateSomeColumnById(Client client) {
        return baseMapper.alwaysUpdateSomeColumnById(client);
    }
}
