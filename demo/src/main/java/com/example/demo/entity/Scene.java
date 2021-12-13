package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scene")
public class Scene {
    @Id
    @Column(name = "id")
    public Integer id;

    @Column(name = "sene_id")
    public String seneId;

    @Column(name = "sene_name")
    public String seneName;

    @Column(name = "expression")
    public String expression;

    @Column(name = "memo")
    public String memo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeneId() {
        return seneId;
    }

    public void setSeneId(String seneId) {
        this.seneId = seneId;
    }

    public String getSeneName() {
        return seneName;
    }

    public void setSeneName(String seneName) {
        this.seneName = seneName;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
