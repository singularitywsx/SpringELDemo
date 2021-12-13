package com.example.demo.respository;

import com.example.demo.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRespository extends JpaRepository<Rule, Integer> {

    //@Query查詢
    @Query(value = "select * from rule where rule_id = ? ", nativeQuery = true)
    List<Rule> findByRuleId(@Param("rule_id") String id);

}
