package com.winwintek.web.backend.api.repository.admin.corporate.internCorporateSurvey;

import com.winwintek.web.backend.api.entity.admin.corporate.InternCorporateSurveyEntity.InternCorporateSurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternCorporateSurveyRepository extends JpaRepository<InternCorporateSurveyEntity, String> {

    InternCorporateSurveyEntity findByInternPlanSeqAndDelYn(String internPlanSeq, String delYn);

}
