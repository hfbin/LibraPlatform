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

package cn.hfbin.plugin.common.entity;

import java.io.Serializable;
import java.util.*;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2022/2/01 11:11 下午
 * @description: 路由实体
 */
public class GatewayRoute implements Serializable {
    private static final long serialVersionUID = 8753685181409614365L;

    private String id;
    private String uri;
    private List<String> predicates = new ArrayList<>();
    private List<String> filters = new ArrayList<>();
    private int order = 0;
    private Map<String, Object> metadata = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<String> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<String> predicates) {
        this.predicates = predicates;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        if (metadata != null) {
            this.metadata = metadata;
        }
    }

    public static class Predicate extends Clause {
        private static final long serialVersionUID = -5020870935530926132L;
    }

    public static class Filter extends Clause {
        private static final long serialVersionUID = 1537210609621458021L;
    }

    public static class Clause implements Serializable {
        private static final long serialVersionUID = 1478260933880659272L;
        private String name;
        private Map<String, String> args = new LinkedHashMap<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, String> getArgs() {
            return args;
        }

        public void setArgs(Map<String, String> args) {
            this.args = args;
        }

        public void addArg(String key, String value) {
            this.args.put(key, value);
        }
    }
}
