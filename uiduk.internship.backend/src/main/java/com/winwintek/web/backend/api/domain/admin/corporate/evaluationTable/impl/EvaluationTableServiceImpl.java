package com.winwintek.web.backend.api.domain.admin.corporate.evaluationTable.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.corporate.evaluationTable.service.EvaluationTableService;
import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.EvaluationTableDetailDto;
import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.EvaluationTableRequestDto;
import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.evaluationCriteria.EvaluationCriteriaDto;
import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.evaluationCriteria.EvaluationCriteriaParentDto;
import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.evaluationStd.EvaluationStdDto;
import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.evaluationStd.InternshipEvaluatorDto;
import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.evaluationStd.StudentInfoDto;
import com.winwintek.web.backend.api.entity.admin.evaluationCriteria.EvaluationCriteriaEntity;
import com.winwintek.web.backend.api.entity.admin.evaluationStd.EvaluationStdDetailResponse;
import com.winwintek.web.backend.api.entity.admin.evaluationStd.EvaluationStdEntity;
import com.winwintek.web.backend.api.entity.admin.evaluationStd.EvaluationStdResponse;
import com.winwintek.web.backend.api.entity.admin.evaluationStd.InternshipEvaluatorResponse;
import com.winwintek.web.backend.api.entity.admin.sudentEvaluation.StudentEvaluationEntity;
import com.winwintek.web.backend.api.repository.admin.evaluationCriteria.EvaluationCriteriaRepository;
import com.winwintek.web.backend.api.repository.admin.evaluationStd.EvaluationStdRepository;
import com.winwintek.web.backend.api.repository.admin.studentEvaluation.StudentEvaluationRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_EVALUATION_STD;
import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_STUDENT_EVALUATION;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_EVALUATION_STD;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_STUDENT_EVALUATION;


@Service
public class EvaluationTableServiceImpl implements EvaluationTableService {

    @Autowired
    private EvaluationStdRepository evaluationStdRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EvaluationCriteriaRepository evaluationCriteriaRepository;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private StudentEvaluationRepository studentEvaluationRepository;


    public List<StudentInfoDto> getStudentInfo(String data) {
        List<EvaluationStdResponse> res = evaluationStdRepository.getStudentInfo(data, Constants.STATE_N,
                Constants.STATUS_APPROVE_02);
        List<StudentInfoDto> studentInfoDto = res.stream().map(item -> modelMapper.map(item, StudentInfoDto.class))
                .collect(Collectors.toList());
        return studentInfoDto;
    }

    public List<EvaluationCriteriaParentDto> getEvaluationTable(String evaluationStdSeq) {
        List<EvaluationCriteriaEntity> listEvaluationCriteria = evaluationCriteriaRepository.findAllByDelYn(
                Constants.STATE_N);
        List<EvaluationCriteriaParentDto> listNameParent = new ArrayList<>();
        List<StudentEvaluationEntity> listStudentEvaluation = studentEvaluationRepository.findAllByDelYnAndEvaluationStdSeq(
                Constants.STATE_N, evaluationStdSeq);
        Set<String> uniqueCriteriaFields = new HashSet<>();
        for (EvaluationCriteriaEntity evaluation : listEvaluationCriteria) {
            String criteriaField = evaluation.getCriteriaField();
            if (! uniqueCriteriaFields.contains(criteriaField)) {
                uniqueCriteriaFields.add(criteriaField);

                EvaluationCriteriaParentDto parentDto = new EvaluationCriteriaParentDto();
                parentDto.setCriteriaField(criteriaField);

                listNameParent.add(parentDto);
            }
        }
        for (EvaluationCriteriaParentDto parentDto : listNameParent) {
            List<EvaluationCriteriaDto> listCriteriaChild = new ArrayList<>();
            for (EvaluationCriteriaEntity entity : listEvaluationCriteria) {
                if (parentDto.getCriteriaField().equals(entity.getCriteriaField())) {
                    EvaluationCriteriaDto criteriaDto = modelMapper.map(entity, EvaluationCriteriaDto.class);
                    for (StudentEvaluationEntity studentEvaluation : listStudentEvaluation) {
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

    @Override
    @Transactional
    public Boolean save(EvaluationTableRequestDto dto) {
        EvaluationStdEntity evaluationStd = modelMapper.map(dto, EvaluationStdEntity.class);
        if (StringUtils.isBlank(dto.getEvaluationStdSeq())) {
            evaluationStd.setEvaluationStdSeq(sequenceGenerator.getNextSequence(PREFIX_EVALUATION_STD.toString(),
                    TABLE_EVALUATION_STD.toString()));
        }
        evaluationStd.setDelYn(Constants.STATE_N);
        evaluationStd.setEvaluationSubmitDate(new Date());
        evaluationStdRepository.save(evaluationStd);
        List<StudentEvaluationEntity> listStdEvaluation = dto.getListStdEvaluation().stream().map(item -> {
            StudentEvaluationEntity studentEvaluation = modelMapper.map(item, StudentEvaluationEntity.class);
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

    public InternshipEvaluatorDto getInternshipEvaluator(String data) {
        InternshipEvaluatorResponse res = evaluationStdRepository.getInternshipEvaluator(data);
        InternshipEvaluatorDto dto = modelMapper.map(res, InternshipEvaluatorDto.class);
        return dto;
    }

    @Override
    public EvaluationStdDto getEvaluationStd(EvaluationTableDetailDto data) {
        EvaluationStdDetailResponse res = evaluationStdRepository.getEvaluationStdDetail(data.getStdNo(),
                data.getInternPlanSeq(), Constants.STATUS_APPROVE_02);
        ClobJavaType clobJavaType = new ClobJavaType();
        EvaluationStdDto evaluationStdDto = new EvaluationStdDto();
        if (Objects.nonNull(res)) {
            evaluationStdDto = modelMapper.map(res, EvaluationStdDto.class);
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

}
