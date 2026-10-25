package com.winwintek.web.backend.api.repository.admin.evaluationCriteria;

import com.winwintek.web.backend.api.entity.admin.evaluationCriteria.EvaluationCriteriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationCriteriaRepository extends JpaRepository<EvaluationCriteriaEntity, String> {

    List<EvaluationCriteriaEntity> findAllByDelYn(String delYn);

}
