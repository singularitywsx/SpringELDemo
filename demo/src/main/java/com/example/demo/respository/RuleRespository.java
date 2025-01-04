package com.example.demo.respository;

import com.example.demo.entity.RuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRespository extends JpaRepository<RuleEntity, Integer> {

    //@Query查詢
    @Query(value = "select * from rule where rule_code = ? ", nativeQuery = true)
    List<RuleEntity> findByRuleCode(@Param("rule_code") String code);

}
