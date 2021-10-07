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

import cn.hfbin.common.core.context.SpringContextUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2019-09-29 22:37
 * @description: 自动填充创建人 时间 https://mp.baomidou.com/guide/auto-fill-metainfo.html
 */
@Slf4j
@Component
public class MybatisInterceptor implements MetaObjectHandler {

    /**
     * 如果请求的实体有参数则默认不会替换值
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        if (metaObject.hasGetter("createTime")) {
            this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        }
        if (metaObject.hasGetter("createBy")) {
            this.strictInsertFill(metaObject, "createBy", Long.class, SpringContextUtils.getIdentityId());
        }
        if (metaObject.hasGetter("updateTime")) {
            this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        }
        if (metaObject.hasGetter("updateBy")) {
            this.strictInsertFill(metaObject, "updateBy", Long.class, SpringContextUtils.getIdentityId());
        }
        if (metaObject.hasGetter("delFlag")) {
            this.strictInsertFill(metaObject, "delFlag", Integer.class, 0);
        }
        if (metaObject.hasGetter("tenantCode")) {
            this.strictInsertFill(metaObject, "tenantCode", String.class, SpringContextUtils.getTenantCode());
        }
    }

    /**
     * 如果请求的实体有参数则默认不会替换值
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        if (metaObject.hasGetter("updateTime")) {
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        }
        if (metaObject.hasGetter("updateBy")) {
            this.strictUpdateFill(metaObject, "updateBy", Long.class, SpringContextUtils.getIdentityId());
        }
    }
}
