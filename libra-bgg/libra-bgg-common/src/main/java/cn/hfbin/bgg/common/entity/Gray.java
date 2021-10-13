package cn.hfbin.bgg.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: huangfubin
 * @Description: Rule 类
 * @Date: 2021/10/13
 */
public class Gray implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Condition> conditionList;

    private String basicCondition;

    public Gray(List<Condition> conditionList, String basicCondition) {
        this.conditionList = conditionList;
        this.basicCondition = basicCondition;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<Condition> conditionList) {
        this.conditionList = conditionList;
    }

    public String getBasicCondition() {
        return basicCondition;
    }

    public void setBasicCondition(String basicCondition) {
        this.basicCondition = basicCondition;
    }
}
