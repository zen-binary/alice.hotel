package com.winwintek.web.backend.api.repository.admin.corporate.internCorporateSurvey;

import com.winwintek.web.backend.api.entity.admin.corporate.InternCorporateSurveyEntity.CorporateSurveyAnsEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateSurveyAnsRepository extends JpaRepository<CorporateSurveyAnsEntity, String> {

    List<CorporateSurveyAnsEntity> findAllByInternCorporateSurveySeqAndDelYn(String internCorporateSurveySeq,
            String delYn);

}
