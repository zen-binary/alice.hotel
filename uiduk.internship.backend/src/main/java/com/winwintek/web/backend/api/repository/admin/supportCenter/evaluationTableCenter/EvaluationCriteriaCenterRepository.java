package com.winwintek.web.backend.api.repository.admin.supportCenter.evaluationTableCenter;

import com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationTableCenter.EvaluationCriteriaCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationCriteriaCenterRepository extends JpaRepository<EvaluationCriteriaCenterEntity, String> {

    List<EvaluationCriteriaCenterEntity> findAllByDelYn(String delYn);

}
