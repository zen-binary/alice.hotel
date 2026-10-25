package com.winwintek.web.backend.api.domain.client.student.summaryReportStudent.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.client.student.summaryReportStudent.service.SummaryReportStudentService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.client.student.summaryReportStudent.SummaryReportStudentReqDTO;
import com.winwintek.web.backend.api.dto.client.student.summaryReportStudent.SummaryReportStudentResDTO;
import com.winwintek.web.backend.api.dto.system.UserDTO;
import com.winwintek.web.backend.api.entity.client.student.summaryReportStudent.ISummaryReportStudentResponse;
import com.winwintek.web.backend.api.entity.client.student.summaryReportStudent.SummaryReportStudentEntity;
import com.winwintek.web.backend.api.repository.client.student.summaryReportStudent.SummaryReportStudentRepository;
import io.micrometer.common.util.StringUtils;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_STD_SUMMARY_REPORTS;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_STD_SUMMARY_REPORTS;


@Service
public class SummaryReportStudentServiceImpl implements SummaryReportStudentService {


    @Autowired
    private SummaryReportStudentRepository summerReportRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private CommonService commonService;

    @Override
    public SummaryReportStudentResDTO getDetailSummaryReportStudent(String internPlanId) {
        UserDTO userDTO = commonService.getFullUserInfo();

        SummaryReportStudentResDTO detailDTO = new SummaryReportStudentResDTO();
        ISummaryReportStudentResponse result = summerReportRepository.getDetailSummaryReport(userDTO.getUserId(),
                internPlanId, Constants.INTERN_STD_APPROVE_STATUS, Constants.STATE_N);

        if (result != null) {
            detailDTO = modelMapper.map(result, SummaryReportStudentResDTO.class);

            ClobJavaType clobJavaType = new ClobJavaType();
            String content = result.getContent() != null ? clobJavaType.toString(result.getContent()) : "";

            detailDTO.setContent(content);
        }

        return detailDTO;
    }

    @Override
    @Transactional
    public String createSummaryReport(SummaryReportStudentReqDTO dto) {
        Integer checkExist = summerReportRepository.checkExistByPlanIdAndStudentIdAndDelYn(dto.getInternPlanSeq(),
                dto.getInternStdSeq(), Constants.INTERN_STD_APPROVE_STATUS, Constants.INTERN_PLAN_STATUS_ACCEPTED,
                Constants.STATE_N);

        if (checkExist == 0) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        SummaryReportStudentEntity entity = modelMapper.map(dto, SummaryReportStudentEntity.class);

        entity.setStdSummaryReportSeq(sequenceGenerator.getNextSequence(PREFIX_STD_SUMMARY_REPORTS.toString(),
                TABLE_STD_SUMMARY_REPORTS.toString()));
        entity.setInternPlanSeq(dto.getInternPlanSeq());
        entity.setInternStdSeq(dto.getInternStdSeq());
        entity.setDelYn(Constants.STATE_N);

        summerReportRepository.save(entity);

        return entity.getStdSummaryReportSeq();
    }

    @Override
    @Transactional
    public String updateSummaryReport(SummaryReportStudentReqDTO dto) {

        if (StringUtils.isEmpty(dto.getStdSummaryReportSeq())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        SummaryReportStudentEntity entity = summerReportRepository.findByStdSummaryReportSeqAndDelYn(
                        dto.getStdSummaryReportSeq(), Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));
        modelMapper.map(dto, entity);
        summerReportRepository.save(entity);
        return entity.getStdSummaryReportSeq();
    }


}
