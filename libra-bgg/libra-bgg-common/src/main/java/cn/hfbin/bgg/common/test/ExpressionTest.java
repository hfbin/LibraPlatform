package cn.hfbin.bgg.common.test;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeComparator;

import java.lang.reflect.Method;
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
        //定义2个函数,registerFunction和setVariable都可以，不过从语义上面来看用registerFunction更恰当
//        StandardEvaluationContext context = new StandardEvaluationContext();
//        Method parseInt = Integer.class.getDeclaredMethod("parseInt", String.class);
//        context.registerFunction("parseInt1", parseInt);
//        context.setVariable("parseInt2", parseInt);
//
//        ExpressionParser parser = new SpelExpressionParser();
//        System.out.println(parser.parseExpression("#parseInt1('3')").getValue(context, int.class));
//        System.out.println(parser.parseExpression("#parseInt2('3')").getValue(context, int.class));
//
//        String expression1 = "#parseInt1('3') == #parseInt2('3')";
//        boolean result1 = parser.parseExpression(expression1).getValue(context, boolean.class);
//        System.out.println(result1);


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
