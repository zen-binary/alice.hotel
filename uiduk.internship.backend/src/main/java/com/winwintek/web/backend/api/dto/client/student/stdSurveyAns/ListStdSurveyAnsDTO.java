package com.winwintek.web.backend.api.dto.client.student.stdSurveyAns;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListStdSurveyAnsDTO {

    List<StdSurveyAnsCreDTO> listAnsSurvey;

}
