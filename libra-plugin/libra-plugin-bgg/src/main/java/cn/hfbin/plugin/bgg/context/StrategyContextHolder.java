package cn.hfbin.plugin.bgg.context;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/4/17 12:15 上午
 * @description:
 */
public interface StrategyContextHolder {

    String getHeader(String name);

    String getRouteVersion();
}
