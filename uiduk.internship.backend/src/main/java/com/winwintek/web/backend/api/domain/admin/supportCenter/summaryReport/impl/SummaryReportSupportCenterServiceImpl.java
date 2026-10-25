package com.winwintek.web.backend.api.domain.admin.supportCenter.summaryReport.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.supportCenter.summaryReport.service.SummaryReportSupportCenterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.summaryReport.SummaryReportSupportCenterReqDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.summaryReport.SummaryReportSupportCenterResDTO;
import com.winwintek.web.backend.api.entity.admin.supportCenter.summaryReport.ISummaryReportSupportCenterResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.summaryReport.SummaryReportSupportCenterEntity;
import com.winwintek.web.backend.api.repository.admin.supportCenter.summaryReport.SummaryReportSupportCenterRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_STD_SUMMARY_REPORT;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_STD_SUMMARY_REPORT;

@Service
public class SummaryReportSupportCenterServiceImpl implements SummaryReportSupportCenterService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private SummaryReportSupportCenterRepository summaryReportSupportCenterRepository;

    @Override
    public SummaryReportSupportCenterResDTO getDetailSummaryReport(String internStdId) {
        ISummaryReportSupportCenterResponse result = summaryReportSupportCenterRepository.getDetailSummaryReport(
                internStdId, Constants.STATE_N);

        ClobJavaType clobJavaType = new ClobJavaType();
        String content = result.getContent() != null ? clobJavaType.toString(result.getContent()) : "";

        SummaryReportSupportCenterResDTO resultDTO = modelMapper.map(result, SummaryReportSupportCenterResDTO.class);
        resultDTO.setContent(content);

        return resultDTO;
    }

    @Override
    @Transactional
    public String createSummaryReport(SummaryReportSupportCenterReqDTO dto) {
        SummaryReportSupportCenterEntity entity = modelMapper.map(dto, SummaryReportSupportCenterEntity.class);

        entity.setStdSummaryReportSeq(sequenceGenerator.getNextSequence(PREFIX_STD_SUMMARY_REPORT.toString(),
                TABLE_STD_SUMMARY_REPORT.toString()));
        entity.setDelYn(Constants.STATE_N);

        summaryReportSupportCenterRepository.save(entity);

        return entity.getStdSummaryReportSeq();
    }

    @Override
    @Transactional
    public String updateSummaryReport(SummaryReportSupportCenterReqDTO dto) {
        if (StringUtils.isEmpty(dto.getStdSummaryReportSeq())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        SummaryReportSupportCenterEntity entity = summaryReportSupportCenterRepository.findByStdSummaryReportSeqAndDelYn(
                dto.getStdSummaryReportSeq(), Constants.STATE_N);
        modelMapper.map(dto, entity);
        summaryReportSupportCenterRepository.save(entity);

        return entity.getStdSummaryReportSeq();
    }

}
