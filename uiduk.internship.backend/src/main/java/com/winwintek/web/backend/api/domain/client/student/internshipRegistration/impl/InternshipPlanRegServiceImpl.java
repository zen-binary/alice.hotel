package com.winwintek.web.backend.api.domain.client.student.internshipRegistration.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.file.service.FileMngService;
import com.winwintek.web.backend.api.domain.client.student.internshipRegistration.service.IInternshipPlanRegService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.client.student.internPlanDetail.InternPlanDetailResDTO;
import com.winwintek.web.backend.api.dto.client.student.internshipRegistration.DetailInternPlanRegFormResDTO;
import com.winwintek.web.backend.api.dto.client.student.internshipRegistration.InternshipRegReqDTO;
import com.winwintek.web.backend.api.dto.client.student.internshipRegistration.InternshipRegResDTO;
import com.winwintek.web.backend.api.dto.common.fileMng.FileDto;
import com.winwintek.web.backend.api.dto.common.fileMng.FileUploadedInfoDto;
import com.winwintek.web.backend.api.dto.system.UserDTO;
import com.winwintek.web.backend.api.entity.client.student.internPlanDetail.IInternPlanDetailOutput;
import com.winwintek.web.backend.api.entity.client.student.internshipRegistration.DetailInternPlanRegFormOutput;
import com.winwintek.web.backend.api.entity.client.student.internshipRegistration.IInternRegOutput;
import com.winwintek.web.backend.api.repository.client.student.internPlanDetail.InternPlanDetailStudentRepository;
import com.winwintek.web.backend.api.repository.client.student.internshipRegistration.InternRegRepository;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternshipPlanRegServiceImpl implements IInternshipPlanRegService {

    @Autowired
    private InternRegRepository internRegRepository;

    @Autowired
    private InternPlanDetailStudentRepository internPlanDetailStudentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileMngService fileMngService;

    @Autowired
    private CommonService commonService;

    @Override
    public List<InternshipRegResDTO> getInternPlanRegList(InternshipRegReqDTO req) {
        UserDTO userDTO = commonService.getFullUserInfo();
        List<IInternRegOutput> internReg = internRegRepository.getInternPlanRegList(req,
                Constants.INTERN_PLAN_STATUS_ACCEPTED, Constants.INTERN_STD_APPROVE_STATUS,
                Constants.INTERN_STUDENT_APPROVE_STATUS_CANCEL, Constants.STATE_N, userDTO.getUserId());

        String fimFileCategory = Constants.INTERNSHIP_PLAN_FILE_CATEGORY;
        String fimSectionName = Constants.INTERNSHIP_PLAN_SECTION_NAME;

        List<InternshipRegResDTO> responseList = internReg.stream().map(item -> {
            InternshipRegResDTO dto = modelMapper.map(item, InternshipRegResDTO.class);
            String urlFile = getUrlFile(item.getCormSeq(), fimFileCategory, fimSectionName);
            dto.setUrlFile(urlFile);
            return dto;
        }).toList();

        return responseList;
    }

    @Override
    public List<String> getListYear() {
        List<String> getListYear = internRegRepository.getListYear(Constants.STATE_N);
        return getListYear;
    }

    @Override
    public DetailInternPlanRegFormResDTO getRegForm(String id) {
        DetailInternPlanRegFormOutput detailResponse = internRegRepository.getDetailInternPlanRegForm(id,
                Constants.INTERN_PLAN_STATUS_ACCEPTED, Constants.INTERN_STD_APPROVE_STATUS, Constants.STATE_N);

        ClobJavaType clobJavaType = new ClobJavaType();
        String internJobSummary = detailResponse.getInternJobSummary() != null ? clobJavaType.toString(
                detailResponse.getInternJobSummary()) : "";
        String requirements = detailResponse.getRequirements() != null ? clobJavaType.toString(
                detailResponse.getRequirements()) : "";
        DetailInternPlanRegFormResDTO resDTO = modelMapper.map(detailResponse, DetailInternPlanRegFormResDTO.class);
        resDTO.setInternJobSummary(internJobSummary);
        resDTO.setRequirements(requirements);

        List<IInternPlanDetailOutput> internPlanDetail = internPlanDetailStudentRepository.getInternPlanDetail(id,
                Constants.STATE_N);

        List<InternPlanDetailResDTO> internPlanDetailList = internPlanDetail.stream().map(item -> {
            InternPlanDetailResDTO dto = new InternPlanDetailResDTO();
            dto.setWeek(item.getWeek());
            dto.setStartDate(item.getStartDate());
            dto.setEndDate(item.getEndDate());
            String content = item.getContent() != null ? clobJavaType.toString(item.getContent()) : "";
            dto.setContent(content);
            return dto;
        }).collect(Collectors.toList());

        resDTO.setInternPlanDetailResDTO(internPlanDetailList);

        return resDTO;
    }

    private String getUrlFile(String fimReferKeyId, String fimFileCategory, String fimSectionName) {

        FileDto dto = new FileDto();
        dto.setFimReferKeyId(fimReferKeyId);
        dto.setFimFileCategory(fimFileCategory);
        dto.setFimSectionName(fimSectionName);
        List<FileUploadedInfoDto> lstFiles = fileMngService.selectFileUploaded(dto);
        if (! lstFiles.isEmpty()) {
            return lstFiles.getFirst().getUrlFile();
        }
        return null;
    }

}
