package com.example.demo.service.impl;

import com.example.demo.entity.RuleEntity;
import com.example.demo.respository.RuleRespository;
import com.example.demo.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    public RuleRespository ruleRespository;

    @Override
    public List<RuleEntity> findByRuleCode(final String code) {
        return ruleRespository.findByRuleCode(code);
    }
}
