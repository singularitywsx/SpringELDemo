package com.example.demo.controller;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class HelloSpringEl {

    public String helloSpringEl(){
        var SpringEL = "'Hello World'"; //SpringEL 文字模板
        ExpressionParser parser = new SpelExpressionParser(); //SpringEL 表達式解析器

        EvaluationContext context = new StandardEvaluationContext(); //可以在上下文設定變量

        Expression exp = parser.parseExpression(SpringEL);  //解析SpringEL表達式
        return exp.getValue(context,String.class);
    }

    public String callConcat(){
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'.concat('!')");
        return (String) exp.getValue();
    }

    public boolean matches(){
        ExpressionParser parser = new SpelExpressionParser();
        return Boolean.TRUE.equals(parser.parseExpression("'5.00' matches '^-?\\d+(\\.\\d{2})?$'").getValue(Boolean.class));
    }
}
