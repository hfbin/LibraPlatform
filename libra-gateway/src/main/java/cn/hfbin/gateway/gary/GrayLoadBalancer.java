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

package cn.hfbin.gateway.gary;

import cn.hfbin.gateway.weight.MapWeightRandom;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * @Author: huangfubin
 * @Description: GrayLoadBalancer 类
 * @Date: 2021/9/16
 */
public class GrayLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    //服务列表
    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public GrayLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }

    /**
     * 根据不同策略选择指定服务实例
     *
     * @param request：用户请求信息封装对象
     * @return
     */
    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        //获取所有请求头
        DefaultRequestContext requestContext = (DefaultRequestContext) request.getContext();
        RequestData clientRequest = (RequestData) requestContext.getClientRequest();
        HttpHeaders headers = clientRequest.getHeaders();
        //服务列表不为空
        if (Objects.nonNull(serviceInstanceListSupplierProvider)) {
            //获取有效的实例对象
            ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
            //按照指定路由规则查找符合的实例对象
            return supplier.get().next().map(list -> getInstanceResponse(list, headers));
        }
        return null;
    }


    /**
     * 获取实例
     * @param instances
     * @return
     */
    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, HttpHeaders headers) {
        //找不到实例
        if (instances.isEmpty()) {
            return new EmptyResponse();
        } else {
            //获取版本号
            String versionNo = headers.getFirst("bgg-version");
            headers.add("bgg-type", "gateway");
            //权重路由、  根据版本号+权重路由
            return StrUtil.isEmpty(versionNo) ? getServiceInstanceResponseWithWeight(instances) :
                    getServiceInstanceResponseByMtName(instances, versionNo, "version");
        }
    }

    /**
     * 指定属性切流（根据版本进行分发|IP切流）
     *
     * @param instances
     * @param versionNo
     * @return
     */
    private Response<ServiceInstance> getServiceInstanceResponseByMtName(List<ServiceInstance> instances, String versionNo, String mtname) {
        Map<String, String> versionMap = new HashMap<>();
        //versionMap.put("version",versionNo);
        versionMap.put(mtname, versionNo);
        //去除重复
        final Set<Map.Entry<String, String>> attributes = Collections.unmodifiableSet(versionMap.entrySet());

        //定义集合，存储所有复合版本的实例
        List<ServiceInstance> serviceInstances = new ArrayList<>();
        for (ServiceInstance instance : instances) {
            //当前实例的元数据信息
            Map<String, String> metadata = instance.getMetadata();
            //元数据信息中是否包含当前版本号信息
            if (metadata.entrySet().containsAll(attributes)) {
                serviceInstances.add(instance);
            }
        }
        // 如果没用当前版本服务则返回此所有服务
        if(CollectionUtil.isEmpty(serviceInstances)){
            serviceInstances.addAll(instances);
        }
        //权重分发
        return getServiceInstanceResponseWithWeight(serviceInstances);
    }

    /**
     * 根据在nacos中配置的权重值，进行分发
     *
     * @param instances
     * @return
     */
    private Response<ServiceInstance> getServiceInstanceResponseWithWeight(List<ServiceInstance> instances) {
        //循环所有实例
        List<Pair<ServiceInstance, Integer>> weightList = new ArrayList<>();
        for (ServiceInstance instance : instances) {
            //获取当前实例元信息
            Map<String, String> metadata = instance.getMetadata();
            //如果当前元信息包含权重key
            if (metadata.containsKey("nacos.weight")) {
                weightList.add(new ImmutablePair<>(instance, Double.valueOf(metadata.get("nacos.weight")).intValue()));

            }
        }
        MapWeightRandom<ServiceInstance, Integer> weightRandom = new MapWeightRandom<>(weightList);
        ServiceInstance serviceInstance = weightRandom.random();

        if (Objects.nonNull(serviceInstance)){
            return new DefaultResponse(serviceInstance);
        }

        //返回空实例
        return new EmptyResponse();
    }
}
