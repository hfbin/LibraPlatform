package cn.hfbin.bgg.common.context;

import cn.hfbin.bgg.common.constant.BggConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

/**
 * @Author: huangfubin
 * @Description: PluginContextAware 类
 * @Date: 2021/10/14
 */
public class PluginContextAware implements ApplicationContextAware{
    private ApplicationContext applicationContext;
    private Environment environment;

    private static ApplicationContext staticApplicationContext;
    private static Environment staticEnvironment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.environment = applicationContext.getEnvironment();

        staticApplicationContext = applicationContext;
        staticEnvironment = applicationContext.getEnvironment();
    }

    public Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    public Object getBean(String name, Object... args) throws BeansException {
        return applicationContext.getBean(name, args);
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }

    public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
        return applicationContext.getBean(requiredType, args);
    }

    public String getConfigPath() {
        return BggConstant.CONFIG_PATH;
    }
}
