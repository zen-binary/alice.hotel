package com.winwintek.web.backend.api.domain.admin.supportCenter.evaluationTableCenter.service;

import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.EvaluationTableDetailDto;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter.EvaluationStdCenterDto;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter.EvaluationTableCenterRequestDto;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter.EvaluationTableDetailCenterDto;

public interface EvaluationTableCenterService {

    Boolean save(EvaluationTableCenterRequestDto dto);

    EvaluationStdCenterDto getEvaluationStd(EvaluationTableDetailCenterDto data);
}
