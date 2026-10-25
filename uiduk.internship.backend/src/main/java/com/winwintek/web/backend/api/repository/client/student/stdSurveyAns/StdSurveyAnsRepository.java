package com.winwintek.web.backend.api.repository.client.student.stdSurveyAns;

import com.winwintek.web.backend.api.entity.client.student.stdSurveyAns.StdSurveyAnsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StdSurveyAnsRepository extends JpaRepository<StdSurveyAnsEntity, String> {

}

