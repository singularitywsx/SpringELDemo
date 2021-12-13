package com.example.demo.service;

import com.example.demo.entity.Rule;

import java.util.List;

public interface RuleService {

    List<Rule> findByRuleId(final String id);
}
