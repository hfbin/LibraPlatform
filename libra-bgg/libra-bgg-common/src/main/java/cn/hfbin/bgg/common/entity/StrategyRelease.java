package cn.hfbin.bgg.common.entity;

import java.io.Serializable;

/**
 * @Author: huangfubin
 * @Description: Rule 类
 * @Date: 2021/10/13
 */
public class StrategyRelease implements Serializable {
    private static final long serialVersionUID = 1L;

    private BlueGreen blueGreen;

    private Gray gray;

    public BlueGreen getBlueGreen() {
        return blueGreen;
    }

    public void setBlueGreen(BlueGreen blueGreen) {
        this.blueGreen = blueGreen;
    }

    public Gray getGray() {
        return gray;
    }

    public void setGray(Gray gray) {
        this.gray = gray;
    }
}
