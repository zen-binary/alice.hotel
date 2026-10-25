package com.winwintek.web.backend.api.repository.admin.studentEvaluation;

import com.winwintek.web.backend.api.entity.admin.sudentEvaluation.StudentEvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentEvaluationRepository extends JpaRepository<StudentEvaluationEntity, String> {

    List<StudentEvaluationEntity> findAllByDelYnAndEvaluationStdSeq(String delYn, String evaluationStdSeq);

}
