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


package cn.hfbin.bgg.gateway.route;
import cn.hfbin.bgg.gateway.entity.GatewayRouteEntity;
import java.util.List;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/2/02 11:17 下午
 * @description: gateway route operation
 */
public interface GatewayRoute {
    void add(GatewayRouteEntity gatewayStrategyRouteEntity);

    void update(GatewayRouteEntity gatewayStrategyRouteEntity);

    void delete(String routeId);

    void updateAll(String gatewayStrategyRouteConfig);

    List<GatewayRouteEntity> allList();
}
