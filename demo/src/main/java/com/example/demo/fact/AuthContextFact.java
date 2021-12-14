package com.example.demo.fact;

import java.io.Serializable;

public class AuthContextFact implements Serializable {
    private String rule;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
