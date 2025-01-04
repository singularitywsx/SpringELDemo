package com.example.demo.controller;

import com.example.demo.dto.AuthResultResponse;
import com.example.demo.dto.AuthResultResponseBody;
import com.example.demo.dto.CommonHeader;
import com.example.demo.entity.RuleEntity;
import com.example.demo.entity.SceneEntity;
import com.example.demo.fact.AuthContextFact;
import com.example.demo.fact.UserFact;
import com.example.demo.service.RuleService;
import com.example.demo.service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


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

    @RequestMapping(value = "/param", method = {GET})
    public AuthResultResponse helloSpringEL(
            @RequestParam("rule") final String rule,
            @RequestParam("age") final Integer age,
            @RequestParam("nationality") final String nationality
        ) {
        var authContextFact = new AuthContextFact();
        var sceneMap =getAllScene();
        authContextFact.setRule(rule);
        var scene = matchScene(sceneMap,authContextFact);
        if(Objects.nonNull(scene)){
            var rules = getRuleExpression(scene);
            var userFact = new UserFact();
            userFact.setExists(true);
            userFact.setStatus("ENABLE");
            userFact.setAge(age);
            userFact.setNationality(nationality);
            return makeResponse(rules,userFact,rule);
        }else{
            return makeNullScene(rule);
        }
    }

    private AuthResultResponse makeResponse(final List<RuleEntity> ruleEntities, final UserFact userFact, final String rule){
        var message = new ArrayList<String>();
        for (var s: ruleEntities) {
            if(!marchRule(s.getExpression(),userFact)) {
                message.add(s.getMessage());
            }
        }
        return makeAuthResultResponse(rule,message);
    }

    private AuthResultResponse makeNullScene(final String ruleCode){
        var result = new AuthResultResponse();
        var head = new CommonHeader();
        var body = new AuthResultResponseBody();

        head.setId(ruleCode);
        body.setPass(false);
        body.setReason("not find Scene" );
        result.setHeader(head);
        result.setBody(body);
        return result;
    }


    private boolean marchRule(final String SpringEL,final UserFact userFact){
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("UserFact",userFact);

        ExpressionParser parser =new SpelExpressionParser();
        return Boolean.TRUE.equals(parser.parseExpression(SpringEL).getValue(context, Boolean.class));
    }

    private List<RuleEntity> getRuleExpression(final SceneEntity sceneEntity){
        return ruleService.findByRuleCode(sceneEntity.getRuleCode());
    }

    private SceneEntity matchScene(final Map<String, SceneEntity> sceneMap, final AuthContextFact authContextFact){
        for(Map.Entry<String, SceneEntity> s :sceneMap.entrySet()){
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setVariable("AuthContextFact",authContextFact);

            ExpressionParser parser = new SpelExpressionParser();
            if(Boolean.TRUE.equals(parser.parseExpression(s.getKey()).getValue(context, Boolean.class))){
                return s.getValue();
            }
        }
        return null;
    }

    private Map<String, SceneEntity> getAllScene(){
        Map<String, SceneEntity> sceneMap = new HashMap<>();
        sceneService.findAll().forEach(sceneEntity -> sceneMap.put(sceneEntity.getExpression(), sceneEntity));
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
