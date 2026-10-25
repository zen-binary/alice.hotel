package com.winwintek.web.backend.api.domain.client.student.stdSurveyAns.impl;

import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.client.student.stdSurveyAns.service.IStdSurveyAnsService;
import com.winwintek.web.backend.api.dto.client.student.stdSurveyAns.ListStdSurveyAnsDTO;
import com.winwintek.web.backend.api.entity.client.student.stdSurveyAns.StdSurveyAnsEntity;
import com.winwintek.web.backend.api.repository.client.student.stdSurveyAns.StdSurveyAnsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.winwintek.web.backend.api.common.GlobalEnum.*;

@Service
public class StdSurveyAnsServiceImpl implements IStdSurveyAnsService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private StdSurveyAnsRepository stdSurveyAnsRepository;

    @Override
    public Boolean create(ListStdSurveyAnsDTO listStdSurveyAnsDTO) {
        List<StdSurveyAnsEntity> stdSurveyAnsEntities = listStdSurveyAnsDTO.getListAnsSurvey().stream().map(dto -> {
            StdSurveyAnsEntity entity = modelMapper.map(dto, StdSurveyAnsEntity.class);
            entity.setStdSurveyAnsSeq(sequenceGenerator.getNextSequence(PREFIX_STD_SURVEY_ANS.toString(),
                    TABLE_STD_SURVEY_ANS.toString()));
            entity.setDelYn(STATE_N.toString());
            return entity;
        }).toList();

        List<StdSurveyAnsEntity> savedEntities = stdSurveyAnsRepository.saveAll(stdSurveyAnsEntities);

        return savedEntities.size() == stdSurveyAnsEntities.size();
    }

    @Override
    public Boolean update(ListStdSurveyAnsDTO listStdSurveyAnsDTO) {
        List<StdSurveyAnsEntity> stdSurveyAnsEntities = listStdSurveyAnsDTO.getListAnsSurvey().stream().map(dto -> {
            StdSurveyAnsEntity entity = modelMapper.map(dto, StdSurveyAnsEntity.class);
            entity.setDelYn(STATE_N.toString());
            return entity;
        }).toList();

        List<StdSurveyAnsEntity> savedEntities = stdSurveyAnsRepository.saveAll(stdSurveyAnsEntities);

        return savedEntities.size() == stdSurveyAnsEntities.size();
    }

}
