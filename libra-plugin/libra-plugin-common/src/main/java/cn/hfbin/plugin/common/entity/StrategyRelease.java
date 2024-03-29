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

/**
 * @Author: huangfubin
 * @Description: Rule 类
 * @Date: 2021/10/13
 */
public class StrategyRelease implements Serializable {
    private static final long serialVersionUID = 3698676565717246478L;
    private BlueGreenGray blueGreen;

    private BlueGreenGray gray;

    public BlueGreenGray getBlueGreen() {
        return blueGreen;
    }

    public void setBlueGreen(BlueGreenGray blueGreen) {
        this.blueGreen = blueGreen;
    }

    public BlueGreenGray getGray() {
        return gray;
    }

    public void setGray(BlueGreenGray gray) {
        this.gray = gray;
    }
}
