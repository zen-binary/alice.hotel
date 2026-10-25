package com.winwintek.web.backend.api.dto.client.student.stdSurveyAns;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StdSurveyAnsCreDTO {

    private String stdSurveyAnsSeq;

    private String internStdSurveySeq;

    private String stdSurveyQusSeq;

    private Integer score;

}
