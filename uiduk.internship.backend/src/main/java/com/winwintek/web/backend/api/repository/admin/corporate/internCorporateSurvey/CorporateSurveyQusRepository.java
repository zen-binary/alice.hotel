package com.winwintek.web.backend.api.repository.admin.corporate.internCorporateSurvey;

import com.winwintek.web.backend.api.entity.admin.corporate.InternCorporateSurveyEntity.CorporateSurveyQusEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateSurveyQusRepository extends JpaRepository<CorporateSurveyQusEntity, String> {

    List<CorporateSurveyQusEntity> findAllByDelYnOrderByDisplayOrder(String delYn);

}
