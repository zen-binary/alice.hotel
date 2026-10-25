package com.winwintek.web.backend.api.repository.admin.corporate.internCorporateSurvey;

import com.winwintek.web.backend.api.entity.admin.corporate.InternCorporateSurveyEntity.InternPlanStdCorpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternPlanStdCorpRepository extends JpaRepository<InternPlanStdCorpEntity, String> {

    Long countAllByInternPlanSeqAndApproveStatusAndDelYn(String internPlanSeq, String approveStatus, String delYn);

}
