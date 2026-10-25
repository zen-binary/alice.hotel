package com.winwintek.web.backend.api.domain.admin.supportCenter.corpSurvey.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.domain.admin.supportCenter.corpSurvey.service.CorpSurveySupportCenterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.corpSurvey.CorpSurveyQuestionResDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.corpSurvey.CorpSurveySupportCenterResDTO;
import com.winwintek.web.backend.api.entity.admin.supportCenter.corpSurvey.ICorpSurveyQuestionResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.corpSurvey.IPlanInternInfoResponse;
import com.winwintek.web.backend.api.repository.admin.supportCenter.corpSurvey.CorpSurveySupportCenterRepository;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorpSurveySupportCenterServiceImpl implements CorpSurveySupportCenterService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CorpSurveySupportCenterRepository corpSurveySupportCenterRepository;

    @Override
    public CorpSurveySupportCenterResDTO getDetailCorporateSurvey(String internPlanId) {
        CorpSurveySupportCenterResDTO resultDTO = new CorpSurveySupportCenterResDTO();
        //get plan info by internPlanId
        IPlanInternInfoResponse planInfo = corpSurveySupportCenterRepository.getPlanInfoByPlanId(internPlanId,
                Constants.STATE_N);

        if (planInfo != null) {
            ClobJavaType clobJavaType = new ClobJavaType();
            String recommend = planInfo.getRecommend() != null ? clobJavaType.toString(planInfo.getRecommend()) : "";
            resultDTO = modelMapper.map(planInfo, CorpSurveySupportCenterResDTO.class);

            resultDTO.setRecommend(recommend);
        }

        //get list survey question by corpSurveyId
        List<ICorpSurveyQuestionResponse> listSurveyQuest = corpSurveySupportCenterRepository.getListSurveyQuestion(
                resultDTO.getCorpSurveyId(), Constants.STATE_N);

        List<CorpSurveyQuestionResDTO> listSurveyQuestDTO = listSurveyQuest.stream()
                .map(item -> modelMapper.map(item, CorpSurveyQuestionResDTO.class)).toList();

        resultDTO.setLstSurveyQuest(listSurveyQuestDTO);

        return resultDTO;
    }

}
