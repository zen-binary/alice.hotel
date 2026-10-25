package com.winwintek.web.backend.api.domain.admin.corporate.evaluationTable.service;

import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.EvaluationTableDetailDto;
import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.EvaluationTableRequestDto;
import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.evaluationStd.EvaluationStdDto;

public interface EvaluationTableService {

    Boolean save(EvaluationTableRequestDto dto);

    EvaluationStdDto getEvaluationStd(EvaluationTableDetailDto data);
}
