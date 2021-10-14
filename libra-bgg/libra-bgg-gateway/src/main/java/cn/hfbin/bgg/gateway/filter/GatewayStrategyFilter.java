package cn.hfbin.bgg.gateway.filter;


import cn.hfbin.bgg.common.filter.StrategyRouteFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;

/**
 * @Author: huangfubin
 * @Description: GatewayStrategyFilter 类
 * @Date: 2021/10/14
 */
public interface GatewayStrategyFilter extends GlobalFilter, Ordered, StrategyRouteFilter {

}
