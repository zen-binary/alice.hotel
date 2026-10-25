package com.winwintek.web.backend.api.domain.admin.supportCenter.guidanceReportCenter.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.supportCenter.guidanceReportCenter.service.GuidanceReportCenterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.guidanceReportCenter.GuidanceReportCenterDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.guidanceReportCenter.GuidanceReportCenterDetailSearchDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.guidanceReportCenter.GuidanceReportCenterReqDTO;
import com.winwintek.web.backend.api.entity.admin.supportCenter.guidanceReportCenter.GuidanceReportCenterEntity;
import com.winwintek.web.backend.api.entity.admin.supportCenter.guidanceReportCenter.IGuidanceReportCenterDetailResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.guidanceReportCenter.IStudentInfoGuidanceCenterResponse;
import com.winwintek.web.backend.api.repository.admin.supportCenter.guidanceReportCenter.GuidanceReportCenterRepository;
import com.winwintek.web.backend.api.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_GUIDANCE_REPORT;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_GUIDANCE_REPORT;

@Service
public class GuidanceReportCenterServiceImpl implements GuidanceReportCenterService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private GuidanceReportCenterRepository guidanceReportCenterRepository;

    @Override
    public GuidanceReportCenterDetailResDTO getDetailGuidanceReport(GuidanceReportCenterDetailSearchDTO searchDTO) {
        //get student information evaluation by internStdId
        IStudentInfoGuidanceCenterResponse studentInfo = guidanceReportCenterRepository.getStudentInfoByStdId(
                        searchDTO.getInternStdId(), Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));

        //get detail guidance report by internPlanId and internStdId
        Optional<IGuidanceReportCenterDetailResponse> resultOpt =
                guidanceReportCenterRepository.getDetailGuidanceReport(
                        searchDTO.getInternPlanId(), searchDTO.getInternStdId(), Constants.STATE_N);

        GuidanceReportCenterDetailResDTO resultDTO = modelMapper.map(studentInfo,
                GuidanceReportCenterDetailResDTO.class);

        //if result == null -> create guidance report, else update
        if (resultOpt.isPresent()) {
            IGuidanceReportCenterDetailResponse result = resultOpt.get();
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
    public String createGuidanceReport(GuidanceReportCenterReqDTO dto) {
        Optional<IGuidanceReportCenterDetailResponse> checkRegistration =
                guidanceReportCenterRepository.getDetailGuidanceReport(dto.getInternPlanSeq(), dto.getInternStdSeq(),
                        Constants.STATE_N);
        if (checkRegistration.isPresent()){
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        GuidanceReportCenterEntity entity = modelMapper.map(dto, GuidanceReportCenterEntity.class);

        entity.setGuidanceReportSeq(
                sequenceGenerator.getNextSequence(PREFIX_GUIDANCE_REPORT.toString(), TABLE_GUIDANCE_REPORT.toString()));
        entity.setDelYn(Constants.STATE_N);
        if (StringUtils.isNotEmpty(dto.getInternDate())) {
            entity.setInternDate(Utils.formatDate(dto.getInternDate()));
        }

        guidanceReportCenterRepository.save(entity);

        return entity.getGuidanceReportSeq();
    }

    @Override
    public String updateGuidanceReport(GuidanceReportCenterReqDTO dto) {
        if (StringUtils.isEmpty(dto.getGuidanceReportSeq())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        GuidanceReportCenterEntity entity = guidanceReportCenterRepository.findByGuidanceReportSeqAndDelYn(
                        dto.getGuidanceReportSeq(), Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));
        modelMapper.map(dto, entity);
        if (StringUtils.isNotEmpty(dto.getInternDate())) {
            entity.setInternDate(Utils.formatDate(dto.getInternDate()));
        }
        guidanceReportCenterRepository.save(entity);
        return entity.getGuidanceReportSeq();
    }

}
