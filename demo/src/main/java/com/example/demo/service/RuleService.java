package com.example.demo.service;

import com.example.demo.entity.RuleEntity;

import java.util.List;

public interface RuleService {

    List<RuleEntity> findByRuleCode(final String code);
}
