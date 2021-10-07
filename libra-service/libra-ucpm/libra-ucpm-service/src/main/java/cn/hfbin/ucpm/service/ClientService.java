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

import cn.hfbin.ucpm.entity.Client;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Author: huangfubin
 * @Description: 客户端管理
 * @Date: 2021-08-27
 */
public interface ClientService {
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param client
     * @return
     */
    Page<Client> page(Integer pageNo, Integer pageSize, Client client);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Client getById(Long id);

    /**
     * 新增数据
     * @param client
     * @return
     */
    int insert(Client client);

    /**
     * 更新数据(为null的字段不会更新)
     * @param client
     * @return
     */
    int update(Client client);

    /**
     * 更新数据
     * @param client
     * @return
     */
    int updateSomeColumnById(Client client);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     *
     * @description 查询列表
     * @param
     * @author huangfubin
     * @date 2021/8/27
     * @return java.util.List<BaseClient>
     */
    List<Client> list(Client client);

    /**
     * 根据客户端编码查询详细信息
     * @param clientCode
     * @return
     */
    Client selectByCode(String clientCode);
}
