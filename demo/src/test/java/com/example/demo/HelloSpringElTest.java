package com.example.demo;

import com.example.demo.controller.Car;
import com.example.demo.controller.CommonRuleBean;
import com.example.demo.controller.HelloSpringEl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HelloSpringElTest {
    HelloSpringEl helloSpringEl = new HelloSpringEl();

    @Test
    void helloSpringEl() {
        var expect = "Hello World";
        var actual = helloSpringEl.helloSpringEl();
        assertThat(actual).isNotNull().isEqualTo(expect);
        System.out.println(actual);
    }

    @Test
    void callConcat() {
        var expect = "Hello World!";
        var actual = helloSpringEl.callConcat();
        assertThat(actual).isNotNull().isEqualTo(expect);
    }

    @Test
    void matches() {
        var actual = helloSpringEl.matches();
        assertThat(actual).isNotNull().isEqualTo(true);
    }

    @Test
    void test1() {
        ExpressionParser parser =new SpelExpressionParser();
        var strEL =parser.parseExpression("'Hello World!'").getValue(String.class);
        var intEL = parser.parseExpression("1").getValue(Integer.class);
        var longEL =parser.parseExpression("-1L").getValue(long.class);
        var floatEL =parser.parseExpression("1.1").getValue(Float.class);
        var doubleEL =parser.parseExpression("1.1E+2").getValue(double.class);
        var hexEL =parser.parseExpression("0xa").getValue(Integer.class);
        var trueEL =parser.parseExpression("true").getValue(boolean.class);
        var nullEL =parser.parseExpression("null").getValue(Object.class);

        System.out.println("strEL="+strEL);
        System.out.println("intEL="+intEL);
        System.out.println("longEL="+longEL);
        System.out.println("floatEL="+floatEL);
        System.out.println("doubleEL="+doubleEL);
        System.out.println("hexEL="+hexEL);
        System.out.println("trueEL="+trueEL);
        System.out.println("nullEL="+nullEL);
    }

    @Test
    void test2() {
        ExpressionParser parser =new SpelExpressionParser();
        var intEL = parser.parseExpression("1+2+4+5/2").getValue(Integer.class);
        var booleanEL =parser.parseExpression("1>2").getValue(boolean.class);
        var threeEL = parser.parseExpression("1 > 1? 1:2").getValue(Integer.class);
        var between =parser.parseExpression("5 between {1,10}").getValue(boolean.class);

        System.out.println("intEL="+intEL);
        System.out.println("booleanEL="+booleanEL);
        System.out.println("threeEL="+threeEL);
        System.out.println("between="+between);

    }

    @Test
    void test3() {
        ExpressionParser parser =new SpelExpressionParser();
        var maxEL =parser.parseExpression("T(Integer).MAX_VALUE").getValue(int.class);
        var intEL =parser.parseExpression("T(Integer).parseInt('1')").getValue(int.class);
        var strEL =parser.parseExpression("new String('java')").getValue(String.class);
        var now =parser.parseExpression("T(java.time.LocalDate).now()").getValue(LocalDate.class);

        assert maxEL != null;
        System.out.println(maxEL.equals(Integer.MAX_VALUE));
        System.out.println(intEL);
        System.out.println(strEL);
        System.out.println(now);
    }

    @Test
    void test4() {
        try {
            StandardEvaluationContext context = new StandardEvaluationContext();
            Method parseInt =Integer.class.getDeclaredMethod("parseInt",String.class);
            context.registerFunction("parseInt1",parseInt);
            context.setVariable("parseInt2",parseInt);

            ExpressionParser parser =new SpelExpressionParser();
            System.out.println(parser.parseExpression("#parseInt1('3')").getValue(context,int.class));
            System.out.println(parser.parseExpression("#parseInt2('3')").getValue(context,int.class));

            String expression ="#parseInt1('3') == #parseInt2('3')";
            boolean actual = Boolean.TRUE.equals(parser.parseExpression(expression).getValue(context, boolean.class));
            System.out.println(actual);
            assertThat(actual).isNotNull().isTrue();

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    //避免null
    @Test
    void test5() {
        var car = new Car();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("car",car);

        ExpressionParser parser =new SpelExpressionParser();
        try{
            System.out.println(parser.parseExpression("#car?.name").getValue(context,String.class));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //@方法調用
    @Test
    void test6() {
        CommonRuleBean factory =new CommonRuleBean();

        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(factory));
        ExpressionParser parser =new SpelExpressionParser();
        String expression ="@CommonRuleBean.getHelloWorld()";
        var actual =parser.parseExpression(expression).getValue(context,String.class);
        System.out.println(actual);
        assertThat(actual).isNotNull().isEqualTo("Hello World!");
    }

    //List 宣告, 訪問, 修改
    @Test
    void test7() {
        var collection =new ArrayList<Integer>();
        collection.add(1);
        collection.add(2);
        collection.add(3);
        collection.add(4);

        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("collection", collection);
        ExpressionParser parser =new SpelExpressionParser();
        System.out.println(parser.parseExpression("#collection").getValue(context, Collection.class));
        System.out.println(parser.parseExpression("#collection[0]").getValue(context, Collection.class));
        parser.parseExpression("#collection[0]").setValue(context, 2);
        System.out.println(parser.parseExpression("#collection").getValue(context, Collection.class));
    }

    //List 集合選擇
    @Test
    void test8() {
        var collection =List.of( 1, 2, 3, 4);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("collection", collection);
        ExpressionParser parser =new SpelExpressionParser();
        System.out.println(parser.parseExpression("#collection").getValue(context, Collection.class));
        var result = parser.parseExpression("#collection.?[#this>2]").getValue(context, Collection.class);
        System.out.println(result);
    }

}
