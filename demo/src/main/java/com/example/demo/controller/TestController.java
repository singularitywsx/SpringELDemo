package com.example.demo.controller;

import com.example.demo.dto.AuthResultResponse;
import com.example.demo.dto.AuthResultResponseBody;
import com.example.demo.dto.CommonHeader;
import com.example.demo.entity.Rule;
import com.example.demo.entity.Scene;
import com.example.demo.fact.AuthContextFact;
import com.example.demo.fact.UserFact;
import com.example.demo.service.RuleService;
import com.example.demo.service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    public RuleService ruleService;

    @Autowired
    public SceneService sceneService;

    @GetMapping(value = "/hi")
    @ResponseBody
    public String test() {
        return "OK";
    }

    @RequestMapping(value = "/param", method = { GET, POST })
    public AuthResultResponse helloSpringEL(
            @RequestParam(required = false)final String ruleCode,
            @RequestParam(required = false)final Integer age,
            @RequestParam(required = false)final String nationality
        ) {
        var authContextFact = new AuthContextFact();
        var sceneMap =getAllScene();
        authContextFact.setRuleCode(ruleCode);

        var scene = matchScene(sceneMap,authContextFact);
        var rules = getRuleExpression(scene);

        var userFact = new UserFact();
        userFact.setExists(true);
        userFact.setStatus("ENABLE");
        userFact.setAge(age);
        userFact.setNationality(nationality);

        return makeResponse(rules,userFact,ruleCode);
    }

    private AuthResultResponse makeResponse(final List<Rule> rules, final UserFact userFact,final String ruleCode){
        var message = new ArrayList<String>();
        for (var s:rules) {
            if(!marchRule(s.getExpression(),userFact)) {
                message.add(s.getMessage());
            }
        }
        return makeAuthResultResponse(ruleCode,message);
    }


    private boolean marchRule(final String SpringEL,final UserFact userFact){
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("UserFact",userFact);

        ExpressionParser parser =new SpelExpressionParser();
        return Boolean.TRUE.equals(parser.parseExpression(SpringEL).getValue(context, Boolean.class));
    }

    private List<Rule> getRuleExpression(final Scene scene){
        return ruleService.findByRuleId(scene.getSeneId());
    }

    private Scene matchScene(final Map<String, Scene> sceneMap,final AuthContextFact authContextFact){
        for(Map.Entry<String, Scene> s :sceneMap.entrySet()){
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setVariable("AuthContextFact",authContextFact);

            ExpressionParser parser = new SpelExpressionParser();
            if(Boolean.TRUE.equals(parser.parseExpression(s.getKey()).getValue(context, Boolean.class))){
                return s.getValue();
            }
        }
        return null;
    }

    private Map<String, Scene> getAllScene(){
        Map<String, Scene> sceneMap = new HashMap<>();
        sceneService.findAll().forEach(scene -> sceneMap.put(scene.getExpression(),scene));
        return sceneMap;
    }

    private AuthResultResponse makeAuthResultResponse(final String ruleCode,final List<String> message){
        var result = new AuthResultResponse();
        var head = new CommonHeader();
        var body = new AuthResultResponseBody();

        head.setId(ruleCode);
        body.setPass(message.size() <= 0);
        body.setReason(message.size() <= 0 ? "" : "檢核不通過");
        body.setMessage(message);
        result.setHeader(head);
        result.setBody(body);
        return result;
    }


}
