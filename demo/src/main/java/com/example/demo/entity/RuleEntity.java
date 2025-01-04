package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "RULE")
public class RuleEntity implements Serializable {
    @Id
    @Column(name = "ID")
    public Integer id;

    @Column(name = "RULE_CODE")
    public String ruleCode;

    @Column(name = "RULE_NAME")
    public String ruleName;

    @Column(name = "EXPRESSION")
    public String expression;

    @Column(name = "RULE_TYPE")
    public String ruleType;

    @Column(name = "MESSAGE")
    public String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleId) {
        this.ruleCode = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
