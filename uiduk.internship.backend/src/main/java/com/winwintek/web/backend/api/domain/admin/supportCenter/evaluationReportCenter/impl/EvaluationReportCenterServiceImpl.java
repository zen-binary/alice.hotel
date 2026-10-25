package com.winwintek.web.backend.api.domain.admin.supportCenter.evaluationReportCenter.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.supportCenter.evaluationReportCenter.service.EvaluationReportCenterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationReportCenter.EvaluationReportCenterDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationReportCenter.EvaluationReportCenterDetailSearchDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationReportCenter.EvaluationReportCenterReqDTO;
import com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationReportCenter.EvaluationReportCenterEntity;
import com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationReportCenter.IEvaluationReportDetailCenterResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationReportCenter.IStudentInfoEvaluationCenterResponse;
import com.winwintek.web.backend.api.repository.admin.supportCenter.evaluationReportCenter.EvaluationReportCenterRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_INTERN_PROCESS_EVALUATION;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_INTERN_PROCESS_EVALUATION;

@Service
public class EvaluationReportCenterServiceImpl implements EvaluationReportCenterService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private EvaluationReportCenterRepository evaluationReportCenterRepository;


    @Override
    public EvaluationReportCenterDetailResDTO getDetailEvaluationReport(EvaluationReportCenterDetailSearchDTO dto) {
        //get student information evaluation by internPlanId and internStdId
        IStudentInfoEvaluationCenterResponse studentInfo = evaluationReportCenterRepository.getStudentInfoByStdId(
                        dto.getInternStdId(), Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));

        //get detail evaluation report by internPlanId and internStdId
        Optional<IEvaluationReportDetailCenterResponse> resultOpt = evaluationReportCenterRepository.getDetailEvaluationReport(
                dto.getInternPlanId(), dto.getInternStdId(), Constants.STATE_N);

        EvaluationReportCenterDetailResDTO resultDTO = modelMapper.map(studentInfo,
                EvaluationReportCenterDetailResDTO.class);

        //if result == null -> create guidance report, else update
        if (resultOpt.isPresent()) {
            IEvaluationReportDetailCenterResponse result = resultOpt.get();
            modelMapper.map(result, resultDTO);
            resultDTO.setIsUpdate(Constants.STATE_Y);
        } else {
            resultDTO.setIsUpdate(Constants.STATE_N);
        }

        return resultDTO;
    }

    @Override
    @Transactional
    public String createEvaluationReport(EvaluationReportCenterReqDTO dto) {
        Optional<IEvaluationReportDetailCenterResponse> checkRegistration =
                evaluationReportCenterRepository.getDetailEvaluationReport(dto.getInternPlanSeq(), dto.getInternStdSeq(),
                        Constants.STATE_N);
        if (checkRegistration.isPresent()){
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        EvaluationReportCenterEntity entity = modelMapper.map(dto, EvaluationReportCenterEntity.class);

        entity.setInternProcessEvaluationSeq(
                sequenceGenerator.getNextSequence(PREFIX_INTERN_PROCESS_EVALUATION.toString(),
                        TABLE_INTERN_PROCESS_EVALUATION.toString()));
        entity.setDelYn(Constants.STATE_N);

        evaluationReportCenterRepository.save(entity);

        return entity.getInternProcessEvaluationSeq();
    }

    @Override
    @Transactional
    public String updateEvaluationReport(EvaluationReportCenterReqDTO dto) {
        if (StringUtils.isEmpty(dto.getInternProcessEvaluationSeq())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        EvaluationReportCenterEntity entity = evaluationReportCenterRepository.findByInternProcessEvaluationSeqAndDelYn(
                        dto.getInternProcessEvaluationSeq(), Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));
        modelMapper.map(dto, entity);
        evaluationReportCenterRepository.save(entity);

        return entity.getInternProcessEvaluationSeq();
    }

}
