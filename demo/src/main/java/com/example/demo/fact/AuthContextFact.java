package com.example.demo.fact;

import java.io.Serializable;

public class AuthContextFact implements Serializable {
    private String ruleCode;

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }
}
