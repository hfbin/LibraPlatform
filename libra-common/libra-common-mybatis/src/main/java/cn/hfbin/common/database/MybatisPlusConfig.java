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

package cn.hfbin.common.database;

import cn.hfbin.common.core.constant.ConfigValueConstant;
import cn.hfbin.common.core.context.SpringContextUtils;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Description
 * @anthor hfbin
 * @date 2019/9/30
 */
@Configuration
public class MybatisPlusConfig {
    @Value("${" + ConfigValueConstant.MYBATIS_PLUS_IGNORE_TABLE + ":table}")
    private String ignoreTable;

    @Value("${" + ConfigValueConstant.MYBATIS_PLUS_OPEN_TENANT + ":true}")
    private boolean openTenant;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 多租户处理
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 租户拦截 PaginationInterceptor  https://mp.baomidou.com/guide/interceptor-tenant-line.html
        if(openTenant){
            interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(tenantLineHandler()));
        }
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
    /**
     * 租户相关配置
     * 忽略拼接多租户sql注解 @InterceptorIgnore(tenantLine = "1")【在mapper层上使用】
     * @return SQL解析过滤
     */
    private TenantLineHandler tenantLineHandler() {
        return new TenantLineHandler() {
            /**
             * 获取租户 ID 值表达式，只支持单个 ID 值
             * <p>
             *
             * @return 租户 ID 值表达式
             */
            @Override
            public Expression getTenantId(){
                return new StringValue(SpringContextUtils.getTenantCode());
            }

            /**
             * 获取租户字段名
             * <p>
             * 默认字段名叫: tenant_code
             *
             * @return 租户字段名
             */
            @Override
            public String getTenantIdColumn() {
                return "tenant_code";
            }

            /**
             * 根据表名判断是否忽略拼接多租户条件
             * <p>
             * 默认都要进行解析并拼接多租户条件
             *  忽略拼接多租户sql注解 @InterceptorIgnore(tenantLine = "1")【在mapper层上使用】
             * @param tableName 表名
             * @return 是否忽略, true:表示忽略，false:需要解析并拼接多租户条件
             */
            @Override
            public boolean ignoreTable(String tableName) {
                return ignoreTable.contains(tableName);
            }
        };
    }

    /**
     * sql增强
     * @return
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new DefaultSqlInjector() {
            @Override
            public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
                List<AbstractMethod> methodList = super.getMethodList(mapperClass);
                methodList.add(new InsertBatchSomeColumn());
                methodList.add(new AlwaysUpdateSomeColumnById());
                return methodList;
            }
        };
    }
}
