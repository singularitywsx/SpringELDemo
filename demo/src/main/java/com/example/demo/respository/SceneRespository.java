package com.example.demo.respository;

import com.example.demo.entity.SceneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SceneRespository extends JpaRepository<SceneEntity, Integer> {
}
