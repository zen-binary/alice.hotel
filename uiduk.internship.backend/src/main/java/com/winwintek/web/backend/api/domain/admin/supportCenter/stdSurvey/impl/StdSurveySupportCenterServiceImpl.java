package com.winwintek.web.backend.api.domain.admin.supportCenter.stdSurvey.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.domain.admin.supportCenter.stdSurvey.service.StdSurveySupportCenterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.stdSurvey.StdSurveyQuestionResDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.stdSurvey.StdSurveySupportCenterResDTO;
import com.winwintek.web.backend.api.entity.admin.supportCenter.stdSurvey.IStdInternInfoResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.stdSurvey.IStdSurveyQuestionResponse;
import com.winwintek.web.backend.api.repository.admin.supportCenter.stdSurvey.StdSurveySupportCenterRepository;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StdSurveySupportCenterServiceImpl implements StdSurveySupportCenterService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StdSurveySupportCenterRepository stdSurveySupportCenterRepository;

    @Override
    public StdSurveySupportCenterResDTO getDetailStudentSurvey(String internStdId) {
        StdSurveySupportCenterResDTO resultDTO = new StdSurveySupportCenterResDTO();
        //get plan info by internPlanId
        IStdInternInfoResponse planInfo = stdSurveySupportCenterRepository.getPlanInfoByInternStdId(internStdId,
                Constants.STATE_N);

        if (planInfo != null) {
            ClobJavaType clobJavaType = new ClobJavaType();
            String recommend = planInfo.getRecommend() != null ? clobJavaType.toString(planInfo.getRecommend()) : "";
            resultDTO = modelMapper.map(planInfo, StdSurveySupportCenterResDTO.class);

            resultDTO.setRecommend(recommend);
        }

        //get list survey question by corpSurveyId
        List<IStdSurveyQuestionResponse> listSurveyQuest = stdSurveySupportCenterRepository.getListSurveyQuestion(
                resultDTO.getStdSurveyId(), Constants.STATE_N);

        List<StdSurveyQuestionResDTO> listSurveyQuestDTO = listSurveyQuest.stream()
                .map(item -> modelMapper.map(item, StdSurveyQuestionResDTO.class)).toList();

        resultDTO.setLstSurveyQuest(listSurveyQuestDTO);

        return resultDTO;
    }

}
