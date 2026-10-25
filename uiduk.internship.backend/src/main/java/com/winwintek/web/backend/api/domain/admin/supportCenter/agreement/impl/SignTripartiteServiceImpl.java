package com.winwintek.web.backend.api.domain.admin.supportCenter.agreement.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.file.service.FileMngService;
import com.winwintek.web.backend.api.domain.admin.supportCenter.agreement.service.SignTripartiterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.agreement.SignTripartiteDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.agreement.StudentInternSupportCenterDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.agreement.UpdateListAppendSupportCenterDTO;
import com.winwintek.web.backend.api.dto.common.fileMng.FileDto;
import com.winwintek.web.backend.api.dto.common.fileMng.FileUploadedInfoDto;
import com.winwintek.web.backend.api.entity.admin.supportCenter.agreement.ISignedTripartiteResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.agreement.IStdInternSupportCenterResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.agreement.SignTripartiteAgreementEntity;
import com.winwintek.web.backend.api.repository.admin.supportCenter.agreement.SignTripartiteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SignTripartiteServiceImpl implements SignTripartiterService {

    @Autowired
    private SignTripartiteRepository signTripartiteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileMngService fileMngService;

    @Override
    public SignTripartiteDTO getListSignSupportCenter(String internPlanId) {
        SignTripartiteDTO detailDTO = new SignTripartiteDTO();
        ISignedTripartiteResponse list = signTripartiteRepository.getListSignSupportCenter(internPlanId,
                Constants.CD_INTERN_PLAN_APPROVAL, Constants.CD_CORM_APPROVAL, Constants.STATE_N);
        String fimFileCategory = Constants.INTERNSHIP_PLAN_FILE_CATEGORY;
        String fimSectionName = Constants.SIGN_SECTION_NAME;
        if (list != null) {
            detailDTO = modelMapper.map(list, SignTripartiteDTO.class);
            String urlFile = getUrlFile(detailDTO.getCormSeq(), fimFileCategory, fimSectionName);
            detailDTO.setUrlFile(urlFile);
        }

        return detailDTO;
    }

    @Override
    public List<StudentInternSupportCenterDTO> getListStudentSupportCenter(String internPlanId) {
        List<IStdInternSupportCenterResponse> lstResult = signTripartiteRepository.getListStudentReportSupportCenter(
                internPlanId, Constants.INTERN_STD_APPROVE_STATUS, Constants.STATE_Y, Constants.STATE_Y,
                Constants.STATE_N);
        String fimFileCategory = Constants.INTERNSHIP_PLAN_FILE_CATEGORY;
        String fimSectionName = Constants.SIGN_STUDENT_SECTION_NAME;
        List<StudentInternSupportCenterDTO> lstResultDTO = lstResult.stream()
                .map(item -> modelMapper.map(item, StudentInternSupportCenterDTO.class)).toList();
        for (StudentInternSupportCenterDTO studentInternSupportCenterDTO : lstResultDTO) {
            String urlFile = getUrlFile(studentInternSupportCenterDTO.getTripartiteAgreementSeq(), fimFileCategory, fimSectionName);
            studentInternSupportCenterDTO.setUrlFile(urlFile);
        }
        return lstResultDTO;
    }

    @Override
    @Transactional
    public Boolean appendAllSupportCenter(UpdateListAppendSupportCenterDTO updateListAppendDTO) {
        if (! updateListAppendDTO.getLstId().isEmpty()) {
            List<SignTripartiteAgreementEntity> lstEntity = new ArrayList<>();
            for (String id : updateListAppendDTO.getLstId()) {
                Optional<SignTripartiteAgreementEntity> entityOpt = signTripartiteRepository.findByTripartiteAgreementSeqAndDelYn(
                        id, Constants.STATE_N);
                if (entityOpt.isPresent()) {
                    SignTripartiteAgreementEntity entity = entityOpt.get();
                    entity.setUnivSignedDate(new Date());
                    entity.setUnivSignedYn(Constants.STATE_Y);
                    lstEntity.add(entity);
                }

            }
            signTripartiteRepository.saveAll(lstEntity);
            return true;
        }
        return false;
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
