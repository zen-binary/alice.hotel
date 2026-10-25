package com.winwintek.web.backend.api.domain.client.professor.internPlanLecturer.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.domain.client.professor.internPlanLecturer.service.InternPlanLecturerService;
import com.winwintek.web.backend.api.dto.client.professor.internPlanLecturer.InternPlanDetailLecturerRepDTO;
import com.winwintek.web.backend.api.dto.client.professor.internPlanLecturer.InternPlanLecturerRepDTO;
import com.winwintek.web.backend.api.entity.client.professor.internPlanLecturer.InternPlanDetailLecturerResponse;
import com.winwintek.web.backend.api.entity.client.professor.internPlanLecturer.InternPlanLecturerResponse;
import com.winwintek.web.backend.api.repository.client.professor.internPlanLecturer.InternPlanLecturerRepository;
import com.winwintek.web.backend.api.util.Utils;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternPlanLecturerServiceImpl implements InternPlanLecturerService {

    @Autowired
    private InternPlanLecturerRepository internPlanLecturerRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InternPlanLecturerRepDTO getDetailInternPlan(String internPlanSeq) {
        InternPlanLecturerResponse internPlanLecturer = internPlanLecturerRepo.getInternPlanById(Constants.STATE_N,
                internPlanSeq).orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));
        InternPlanLecturerRepDTO detailDto = modelMapper.map(internPlanLecturer, InternPlanLecturerRepDTO.class);
        detailDto.setRegisterStr(Utils.formatDateToString(internPlanLecturer.getRegisterStr()));
        detailDto.setRegisterEnd(Utils.formatDateToString(internPlanLecturer.getRegisterEnd()));
        detailDto.setInternshipStr(Utils.formatDateToString(internPlanLecturer.getInternshipStr()));
        detailDto.setInternshipEnd(Utils.formatDateToString(internPlanLecturer.getInternshipEnd()));

        ClobJavaType clobJavaType = new ClobJavaType();
        detailDto.setInternJobSummary(internPlanLecturer.getInternJobSummary() != null ? clobJavaType.toString(
                internPlanLecturer.getInternJobSummary()) : null);
        detailDto.setRequirements(internPlanLecturer.getRequirements() != null ? clobJavaType.toString(
                internPlanLecturer.getRequirements()) : null);
        List<InternPlanDetailLecturerResponse> listDetail = internPlanLecturerRepo.getListDetail(internPlanSeq,
                Constants.STATE_N);
        List<InternPlanDetailLecturerRepDTO> listDetailDto = listDetail.stream().map(item -> {
            InternPlanDetailLecturerRepDTO internPlanDetail = modelMapper.map(item,
                    InternPlanDetailLecturerRepDTO.class);
            internPlanDetail.setContent(item.getContent() != null ? clobJavaType.toString(item.getContent()) : null);
            internPlanDetail.setDelYn(item.getDelYn());
            internPlanDetail.setStartDate(Utils.formatDateToString(item.getStartDate()));
            internPlanDetail.setEndDate(Utils.formatDateToString(item.getEndDate()));
            return internPlanDetail;
        }).collect(Collectors.toList());

        detailDto.setListInternPlanDetail(listDetailDto);
        return detailDto;
    }

}
