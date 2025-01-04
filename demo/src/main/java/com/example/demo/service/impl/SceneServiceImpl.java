package com.example.demo.service.impl;

import com.example.demo.entity.SceneEntity;
import com.example.demo.respository.SceneRespository;
import com.example.demo.service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceneServiceImpl implements SceneService {

    @Autowired
    public SceneRespository sceneRespository;


    @Override
    public List<SceneEntity> findAll() {
        return sceneRespository.findAll();
    }
}
