package com.winwintek.web.backend.api.repository.admin.supportCenter.evaluationTableCenter;

import com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationTableCenter.StudentEvaluationCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentEvaluationCenterRepository extends JpaRepository<StudentEvaluationCenterEntity, String> {

    List<StudentEvaluationCenterEntity> findAllByDelYnAndEvaluationStdSeq(String delYn, String evaluationStdSeq);

}
