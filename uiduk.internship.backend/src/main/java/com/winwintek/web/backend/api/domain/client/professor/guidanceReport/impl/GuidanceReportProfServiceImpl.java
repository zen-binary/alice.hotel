package com.winwintek.web.backend.api.domain.client.professor.guidanceReport.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.client.professor.guidanceReport.service.GuidanceReportProfService;
import com.winwintek.web.backend.api.dto.client.professor.guidanceReport.GuidanceReportProfDetailResDTO;
import com.winwintek.web.backend.api.dto.client.professor.guidanceReport.GuidanceReportProfDetailSearchDTO;
import com.winwintek.web.backend.api.dto.client.professor.guidanceReport.GuidanceReportProfReqDTO;
import com.winwintek.web.backend.api.entity.client.professor.guidanceReport.GuidanceReportProfEntity;
import com.winwintek.web.backend.api.entity.client.professor.guidanceReport.IGuidanceReportDetailResponse;
import com.winwintek.web.backend.api.entity.client.professor.guidanceReport.IStudentInfoGuidanceResponse;
import com.winwintek.web.backend.api.repository.client.professor.guidanceReport.GuidanceReportProfRepository;
import com.winwintek.web.backend.api.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_GUIDANCE_REPORT;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_GUIDANCE_REPORT;

@Service
public class GuidanceReportProfServiceImpl implements GuidanceReportProfService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private GuidanceReportProfRepository guidanceReportProfRepository;

    @Override
    public GuidanceReportProfDetailResDTO getDetailGuidanceReport(GuidanceReportProfDetailSearchDTO searchDTO) {
        //get student information evaluation by internStdId
        IStudentInfoGuidanceResponse studentInfo = guidanceReportProfRepository.getStudentInfoByStdId(
                searchDTO.getInternStdId(), Constants.STATE_N);

        //get detail guidance report by internPlanId and internStdId
        IGuidanceReportDetailResponse result = guidanceReportProfRepository.getDetailGuidanceReport(
                searchDTO.getInternPlanId(), searchDTO.getInternStdId(), Constants.STATE_N);

        GuidanceReportProfDetailResDTO resultDTO = modelMapper.map(studentInfo, GuidanceReportProfDetailResDTO.class);

        //if result == null -> create guidance report, else update
        if (result != null) {
            ClobJavaType clobJavaType = new ClobJavaType();
            String internSituation = result.getInternSituation() != null ? clobJavaType.toString(
                    result.getInternSituation()) : "";
            String internContent = result.getInternContent() != null ? clobJavaType.toString(
                    result.getInternContent()) : "";
            String internCompanyFeedback = result.getInternCompanyFeedback() != null ? clobJavaType.toString(
                    result.getInternCompanyFeedback()) : "";
            String internProfFeedback = result.getInternProfFeedback() != null ? clobJavaType.toString(
                    result.getInternProfFeedback()) : "";

            modelMapper.map(result, resultDTO);
            resultDTO.setInternSituation(internSituation);
            resultDTO.setInternContent(internContent);
            resultDTO.setInternCompanyFeedback(internCompanyFeedback);
            resultDTO.setInternProfFeedback(internProfFeedback);
            resultDTO.setIsUpdate(Constants.STATE_Y);
        } else {
            resultDTO.setIsUpdate(Constants.STATE_N);
        }

        return resultDTO;
    }

    @Override
    @Transactional
    public String createGuidanceReport(GuidanceReportProfReqDTO dto) {
        GuidanceReportProfEntity entity = modelMapper.map(dto, GuidanceReportProfEntity.class);

        entity.setGuidanceReportSeq(
                sequenceGenerator.getNextSequence(PREFIX_GUIDANCE_REPORT.toString(), TABLE_GUIDANCE_REPORT.toString()));
        entity.setDelYn(Constants.STATE_N);
        if (StringUtils.isNotEmpty(dto.getInternDate())) {
            entity.setInternDate(Utils.formatDate(dto.getInternDate()));
        }

        guidanceReportProfRepository.save(entity);

        return entity.getGuidanceReportSeq();
    }

    @Override
    @Transactional
    public String updateGuidanceReport(GuidanceReportProfReqDTO dto) {
        if (StringUtils.isEmpty(dto.getGuidanceReportSeq())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        GuidanceReportProfEntity entity = guidanceReportProfRepository.findByGuidanceReportSeqAndDelYn(
                dto.getGuidanceReportSeq(), Constants.STATE_N);
        modelMapper.map(dto, entity);
        if (StringUtils.isNotEmpty(dto.getInternDate())) {
            entity.setInternDate(Utils.formatDate(dto.getInternDate()));
        }

        guidanceReportProfRepository.save(entity);

        return entity.getGuidanceReportSeq();
    }

}
