package com.winwintek.web.backend.api.domain.admin.supportCenter.evaluationTableCenter.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.supportCenter.evaluationTableCenter.service.EvaluationTableCenterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter.EvaluationCriteriaCenterDto;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter.EvaluationCriteriaParentCenterDto;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter.EvaluationStdCenterDto;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter.EvaluationTableCenterRequestDto;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter.EvaluationTableDetailCenterDto;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter.InternshipEvaluatorCenterDto;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter.StudentInfoCenterDto;
import com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationTableCenter.EvaluationCriteriaCenterEntity;
import com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationTableCenter.EvaluationStdCenterEntity;
import com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationTableCenter.EvaluationStdCenterResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationTableCenter.EvaluationStdDetailCenterResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationTableCenter.InternshipEvaluatorCenterResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationTableCenter.StudentEvaluationCenterEntity;
import com.winwintek.web.backend.api.repository.admin.supportCenter.evaluationTableCenter.EvaluationCriteriaCenterRepository;
import com.winwintek.web.backend.api.repository.admin.supportCenter.evaluationTableCenter.EvaluationStdCenterRepository;
import com.winwintek.web.backend.api.repository.admin.supportCenter.evaluationTableCenter.StudentEvaluationCenterRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_EVALUATION_STD;
import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_STUDENT_EVALUATION;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_EVALUATION_STD;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_STUDENT_EVALUATION;

@Service
public class EvaluationTableCenterServiceImpl implements EvaluationTableCenterService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private EvaluationStdCenterRepository evaluationStdRepository;

    @Autowired
    private EvaluationCriteriaCenterRepository evaluationCriteriaRepository;

    @Autowired
    private StudentEvaluationCenterRepository studentEvaluationRepository;


    @Override
    @Transactional
    public Boolean save(EvaluationTableCenterRequestDto dto) {
        EvaluationStdCenterEntity evaluationStd = modelMapper.map(dto, EvaluationStdCenterEntity.class);
        if (StringUtils.isBlank(dto.getEvaluationStdSeq())) {
            evaluationStd.setEvaluationStdSeq(sequenceGenerator.getNextSequence(PREFIX_EVALUATION_STD.toString(),
                    TABLE_EVALUATION_STD.toString()));
        }
        evaluationStd.setDelYn(Constants.STATE_N);
        evaluationStd.setEvaluationSubmitDate(new Date());
        evaluationStdRepository.save(evaluationStd);
        List<StudentEvaluationCenterEntity> listStdEvaluation = dto.getListStdEvaluation().stream().map(item -> {
            StudentEvaluationCenterEntity studentEvaluation = modelMapper.map(item,
                    StudentEvaluationCenterEntity.class);
            if (StringUtils.isBlank(item.getStudentEvaluationSeq())) {
                studentEvaluation.setStudentEvaluationSeq(
                        sequenceGenerator.getNextSequence(PREFIX_STUDENT_EVALUATION.toString(),
                                TABLE_STUDENT_EVALUATION.toString()));
            }
            studentEvaluation.setEvaluationStdSeq(evaluationStd.getEvaluationStdSeq());
            studentEvaluation.setDelYn(Constants.STATE_N);
            return studentEvaluation;
        }).collect(Collectors.toList());
        studentEvaluationRepository.saveAll(listStdEvaluation);
        return true;
    }


    @Override
    public EvaluationStdCenterDto getEvaluationStd(EvaluationTableDetailCenterDto data) {
        EvaluationStdDetailCenterResponse res = evaluationStdRepository.getEvaluationStdDetail(data.getStdNo(),
                data.getInternPlanSeq(), Constants.CD_STD_APPROVAL);
        ClobJavaType clobJavaType = new ClobJavaType();
        EvaluationStdCenterDto evaluationStdDto = new EvaluationStdCenterDto();
        if (Objects.nonNull(res)) {
            evaluationStdDto = modelMapper.map(res, EvaluationStdCenterDto.class);
            evaluationStdDto.setEvaluationFeedback(Objects.nonNull(res.getEvaluationFeedback()) ? clobJavaType.toString(
                    res.getEvaluationFeedback()) : "");
            evaluationStdDto.setStudentFeedback(Objects.nonNull(res.getEvaluationFeedback()) ? clobJavaType.toString(
                    res.getStudentFeedback()) : "");
        } else {
            evaluationStdDto.setEvaluationFeedback("");
            evaluationStdDto.setStudentFeedback("");
        }
        evaluationStdDto.setListStudentInfo(getStudentInfo(data.getInternPlanSeq()));
        evaluationStdDto.setListEvaluationCriteria(getEvaluationTable(evaluationStdDto.getEvaluationStdSeq()));
        evaluationStdDto.setInternshipEvaluatorDto(getInternshipEvaluator(data.getInternPlanSeq()));

        return evaluationStdDto;
    }

    public List<StudentInfoCenterDto> getStudentInfo(String data) {
        List<EvaluationStdCenterResponse> res = evaluationStdRepository.getStudentInfo(data, Constants.STATE_N,
                Constants.STATUS_APPROVE_02);
        List<StudentInfoCenterDto> studentInfoDto = res.stream()
                .map(item -> modelMapper.map(item, StudentInfoCenterDto.class)).collect(Collectors.toList());
        return studentInfoDto;
    }

    public List<EvaluationCriteriaParentCenterDto> getEvaluationTable(String evaluationStdSeq) {
        List<EvaluationCriteriaCenterEntity> listEvaluationCriteria = evaluationCriteriaRepository.findAllByDelYn(
                Constants.STATE_N);
        List<EvaluationCriteriaParentCenterDto> listNameParent = new ArrayList<>();
        List<StudentEvaluationCenterEntity> listStudentEvaluation = studentEvaluationRepository.findAllByDelYnAndEvaluationStdSeq(
                Constants.STATE_N, evaluationStdSeq);
        Set<String> uniqueCriteriaFields = new HashSet<>();
        for (EvaluationCriteriaCenterEntity evaluation : listEvaluationCriteria) {
            String criteriaField = evaluation.getCriteriaField();
            if (! uniqueCriteriaFields.contains(criteriaField)) {
                uniqueCriteriaFields.add(criteriaField);

                EvaluationCriteriaParentCenterDto parentDto = new EvaluationCriteriaParentCenterDto();
                parentDto.setCriteriaField(criteriaField);

                listNameParent.add(parentDto);
            }
        }
        for (EvaluationCriteriaParentCenterDto parentDto : listNameParent) {
            List<EvaluationCriteriaCenterDto> listCriteriaChild = new ArrayList<>();
            for (EvaluationCriteriaCenterEntity entity : listEvaluationCriteria) {
                if (parentDto.getCriteriaField().equals(entity.getCriteriaField())) {
                    EvaluationCriteriaCenterDto criteriaDto = modelMapper.map(entity,
                            EvaluationCriteriaCenterDto.class);
                    for (StudentEvaluationCenterEntity studentEvaluation : listStudentEvaluation) {
                        if (studentEvaluation.getEvaluationCriteriaSeq().equals(entity.getEvaluationCriteriaSeq())) {
                            criteriaDto.setStudentEvaluationSeq(studentEvaluation.getStudentEvaluationSeq());
                            criteriaDto.setScore(studentEvaluation.getScore());
                        }
                    }
                    listCriteriaChild.add(criteriaDto);
                }
            }
            parentDto.setCriteriaChild(listCriteriaChild);
        }

        return listNameParent;
    }

    public InternshipEvaluatorCenterDto getInternshipEvaluator(String data) {
        InternshipEvaluatorCenterResponse res = evaluationStdRepository.getInternshipEvaluator(data)
                .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));
        InternshipEvaluatorCenterDto dto = modelMapper.map(res, InternshipEvaluatorCenterDto.class);
        return dto;
    }

}
