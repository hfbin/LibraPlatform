package cn.hfbin.bgg.common.loader;

import cn.hfbin.bgg.common.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @Author: huangfubin
 * @Description: LocalConfigLoader 类
 * @Date: 2021/10/14
 */
public abstract class LocalConfigLoader implements ConfigLoader{
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public String getConfig(){
        String path = getPath();
        return FileUtil.getText(applicationContext, path);
    }

    protected abstract String getPath();
}
