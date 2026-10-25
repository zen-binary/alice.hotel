package com.winwintek.web.backend.api.domain.client.student.internStdSurvey.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.client.student.internStdSurvey.service.IInternStdSurveyService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.client.student.internStdSurvey.IInternStdSurveyCusDTO;
import com.winwintek.web.backend.api.dto.client.student.internStdSurvey.IStdSurveyAnsCusDTO;
import com.winwintek.web.backend.api.dto.client.student.internStdSurvey.InternStdSurveyCreDTO;
import com.winwintek.web.backend.api.dto.client.student.internStdSurvey.InternStdSurveyDetailReqDTO;
import com.winwintek.web.backend.api.dto.client.student.internStdSurvey.StdSurveyAnsDTO;
import com.winwintek.web.backend.api.dto.system.UserDTO;
import com.winwintek.web.backend.api.entity.client.student.internStdSurvey.InternStdSurveyEntity;
import com.winwintek.web.backend.api.repository.client.student.internStdSurvey.InternStdSurveyRepository;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_INTERN_STD_SURVEY;
import static com.winwintek.web.backend.api.common.GlobalEnum.STATE_N;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_INTERN_STD_SURVEY;

@Service
public class InternStdSurveyServiceImpl implements IInternStdSurveyService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private InternStdSurveyRepository internStdSurveyRepository;

    @Autowired
    private CommonService commonService;

    @Override
    public String create(InternStdSurveyCreDTO internStdSurveyCreDTO) {
        InternStdSurveyEntity internStdSurveyEntity = modelMapper.map(internStdSurveyCreDTO,
                InternStdSurveyEntity.class);

        internStdSurveyEntity.setInternStdSurveySeq(
                sequenceGenerator.getNextSequence(PREFIX_INTERN_STD_SURVEY.toString(),
                        TABLE_INTERN_STD_SURVEY.toString()));
        internStdSurveyEntity.setDelYn(STATE_N.toString());
        internStdSurveyEntity = internStdSurveyRepository.save(internStdSurveyEntity);
        return internStdSurveyEntity.getInternStdSurveySeq();
    }

    @Override
    public String update(InternStdSurveyCreDTO internStdSurveyCreDTO) {
        InternStdSurveyEntity internStdSurveyEntity = modelMapper.map(internStdSurveyCreDTO,
                InternStdSurveyEntity.class);

        internStdSurveyEntity.setDelYn(STATE_N.toString());
        internStdSurveyEntity = internStdSurveyRepository.save(internStdSurveyEntity);
        return internStdSurveyEntity.getInternStdSurveySeq();
    }

    @Override
    public InternStdSurveyDetailReqDTO getDetailStdSurveyDetail(String seq) {
        UserDTO userDTO = commonService.getFullUserInfo();
        ClobJavaType clobJavaType = new ClobJavaType();
        IInternStdSurveyCusDTO internStdSurveyCusDTO = internStdSurveyRepository.getInternStdDetail(seq,
                userDTO.getUserId(), Constants.INTERN_STD_APPROVE_STATUS, Constants.INTERN_PLAN_STATUS_ACCEPTED,
                Constants.STATE_N);
        List<IStdSurveyAnsCusDTO> ansCusDTOList = internStdSurveyRepository.getStdSurveyAnsDetail(
                internStdSurveyCusDTO.getInternStdSurveySeq());
        InternStdSurveyDetailReqDTO resDTO = modelMapper.map(internStdSurveyCusDTO, InternStdSurveyDetailReqDTO.class);
        resDTO.setInternRecommend(internStdSurveyCusDTO.getInternRecommend() != null ? clobJavaType.toString(
                internStdSurveyCusDTO.getInternRecommend()) : "");
        if (ansCusDTOList == null || ansCusDTOList.isEmpty()) {
            resDTO.setStdSurveyAnsDTOList(Collections.emptyList());
        } else {
            List<StdSurveyAnsDTO> stdSurveyAnsDTOList = ansCusDTOList.stream()
                    .map(ans -> modelMapper.map(ans, StdSurveyAnsDTO.class)).collect(Collectors.toList());
            resDTO.setStdSurveyAnsDTOList(stdSurveyAnsDTOList);
        }
        return resDTO;
    }

}
