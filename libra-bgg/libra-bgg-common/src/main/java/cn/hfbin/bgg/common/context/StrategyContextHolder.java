package cn.hfbin.bgg.common.context;

import org.springframework.stereotype.Service;

/**
 * @Author: huangfubin
 * @Description: StrategyContextHolder 类
 * @Date: 2021/10/14
 */
@Service
public interface StrategyContextHolder {

    String getHeader(String name);

    String getRouteVersion();
}
