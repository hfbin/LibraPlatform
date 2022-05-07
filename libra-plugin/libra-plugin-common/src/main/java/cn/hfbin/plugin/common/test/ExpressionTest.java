package cn.hfbin.plugin.common.test;

import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2021/10/16 12:43 上午
 * @description:
 */
public class ExpressionTest {

    public static void main(String[] args) throws NoSuchMethodException {

        StandardEvaluationContext context1 = new StandardEvaluationContext();

        String expression = "#H['tenantCode'] == 'HDDC'";

        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");

        context1.setVariable("H", map);


        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        Boolean result = spelExpressionParser.parseExpression(expression).getValue(context1, Boolean.class);
        System.out.println(result);

    }
}
