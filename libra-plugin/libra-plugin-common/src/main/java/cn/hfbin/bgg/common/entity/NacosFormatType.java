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
package cn.hfbin.bgg.common.entity;

/**
 * @Author: huangfubin
 * @Description:
 * @Date: 2022/01/26
 */
public enum NacosFormatType {
    XML_FORMAT("xml"),
    JSON_FORMAT("json"),
    YAML_FORMAT("yaml"),
    PROPERTIES_FORMAT("properties"),
    HTML_FORMAT("html"),
    TEXT_FORMAT("text");

    private String value;

    NacosFormatType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static NacosFormatType toFormatType(String value) {
        for (NacosFormatType type : NacosFormatType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
