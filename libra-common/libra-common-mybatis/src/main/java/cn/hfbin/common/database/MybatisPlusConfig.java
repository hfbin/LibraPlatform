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
import cn.hutool.core.collection.CollectionUtil;
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
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.WithItem;
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
        // ???????????????
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // ???????????? PaginationInterceptor  https://mp.baomidou.com/guide/interceptor-tenant-line.html
        if(openTenant){
            interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(tenantLineHandler()));
        }
        // ????????????
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
    /**
     * ??????????????????
     * ?????????????????????sql?????? @InterceptorIgnore(tenantLine = "1")??????mapper???????????????
     * @return SQL????????????
     */
    private TenantLineHandler tenantLineHandler() {
        return new TenantLineHandler() {
            /**
             * ???????????? ID ?????????????????????????????? ID ???
             * <p>
             *
             * @return ?????? ID ????????????
             */
            @Override
            public Expression getTenantId(){
                return new StringValue(SpringContextUtils.getTenantCode());
            }

            /**
             * ?????????????????????
             * <p>
             * ??????????????????: tenant_code
             *
             * @return ???????????????
             */
            @Override
            public String getTenantIdColumn() {
                return "tenant_code";
            }

            /**
             * ???????????????????????????????????????????????????
             * <p>
             * ????????????????????????????????????????????????
             *  ?????????????????????sql?????? @InterceptorIgnore(tenantLine = "1")??????mapper???????????????
             * @param tableName ??????
             * @return ????????????, true:???????????????false:????????????????????????????????????
             */
            @Override
            public boolean ignoreTable(String tableName) {
                return ignoreTable.contains(tableName);
            }
        };
    }

    /**
     * sql??????
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
