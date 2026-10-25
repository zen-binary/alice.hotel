package com.winwintek.web.backend.api.domain.admin.corporate.signed.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.file.service.FileMngService;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.corporate.signed.service.SignedService;
import com.winwintek.web.backend.api.dto.admin.corporate.signed.SignedDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.signed.StudentInternCorporate;
import com.winwintek.web.backend.api.dto.admin.corporate.signed.TripartiteAgreeReqDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.signed.UpdateListAppendDTO;
import com.winwintek.web.backend.api.dto.common.fileMng.FileDto;
import com.winwintek.web.backend.api.dto.common.fileMng.FileUploadedInfoDto;
import com.winwintek.web.backend.api.entity.admin.corporate.signed.ISignedResponse;
import com.winwintek.web.backend.api.entity.admin.corporate.signed.IStdInternCorporateResponse;
import com.winwintek.web.backend.api.entity.admin.corporate.signed.TripartiteAgreementCorpEntity;
import com.winwintek.web.backend.api.repository.admin.corporate.signed.SignedRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_TRIPARTITE_AGREEMENT;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_TRIPARTITE_AGREEMENT;

@Service
public class SignedServiceImpl implements SignedService {

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private SignedRepository signedRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileMngService fileMngService;

    @Override
    public SignedDTO getListSign(String internPlanId) {
        SignedDTO detailSignedDTO = new SignedDTO();
        ISignedResponse lstResult = signedRepository.getListSign(internPlanId, Constants.STATE_N);
        String fimFileCategory = Constants.INTERNSHIP_PLAN_FILE_CATEGORY;
        String fimSectionName = Constants.SIGN_SECTION_NAME;
        if (lstResult != null) {
            detailSignedDTO = modelMapper.map(lstResult, SignedDTO.class);
            String urlFile = getUrlFile(detailSignedDTO.getCormSeq(), fimFileCategory, fimSectionName);
            detailSignedDTO.setUrlFile(urlFile);
        }
        return detailSignedDTO;
    }

    @Override
    public List<StudentInternCorporate> getListStudentCorporate(String internPlanId) {
        List<IStdInternCorporateResponse> lstResult = signedRepository.getListStudentReportCorporate(internPlanId,
                Constants.INTERN_STD_APPROVE_STATUS, Constants.STATE_N);

        List<StudentInternCorporate> lstResultDTO = lstResult.stream()
                .map(item -> modelMapper.map(item, StudentInternCorporate.class)).toList();

        return lstResultDTO;
    }

    @Override
    @Transactional
    public Boolean appendAll(UpdateListAppendDTO updateListAppendDTO) {
        if (! updateListAppendDTO.getLstId().isEmpty()) {
            List<TripartiteAgreementCorpEntity> lstEntity = new ArrayList<>();
            for (String id : updateListAppendDTO.getLstId()) {
                Optional<TripartiteAgreementCorpEntity> entityOpt = signedRepository.findByTripartiteAgreementSeqAndDelYn(
                        id, Constants.STATE_N);
                if (entityOpt.isPresent()) {
                    TripartiteAgreementCorpEntity entity = entityOpt.get();
                    entity.setCorporateSignedYn(Constants.STATE_Y);
                    entity.setCorporateSignedDate(new Date());
                    lstEntity.add(entity);
                }

            }
            signedRepository.saveAll(lstEntity);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<StudentInternCorporate> createTripartiteAgree(TripartiteAgreeReqDTO dto) {
        List<StudentInternCorporate> lstResultDTO = new ArrayList<>();
        if (StringUtils.isNotEmpty(dto.getPlanId()) && ! dto.getLstStdId().isEmpty()) {
            List<TripartiteAgreementCorpEntity> lstEntity = new ArrayList<>();
            for (String internStdId : dto.getLstStdId()) {
                //check exist intern student and intern plan
                Integer checkExist = signedRepository.checkExistByPlanIdAndStudentIdAndDelYn(dto.getPlanId(), internStdId,
                        Constants.INTERN_STD_APPROVE_STATUS, Constants.INTERN_PLAN_STATUS_ACCEPTED, Constants.STATE_N);

                if (checkExist == 0) {
                    continue;
                }

                //create tripartite agreement
                TripartiteAgreementCorpEntity entity = createTripartiteAgreementEntity(dto.getPlanId(), internStdId);
                lstEntity.add(entity);
            }

            signedRepository.saveAll(lstEntity);
            lstResultDTO =
                    lstEntity.stream().map(item -> modelMapper.map(item, StudentInternCorporate.class)).toList();
            return lstResultDTO;
        }

        return lstResultDTO;
    }

    private TripartiteAgreementCorpEntity createTripartiteAgreementEntity(String internPlanId, String internStdId) {
        TripartiteAgreementCorpEntity entity = new TripartiteAgreementCorpEntity();

        entity.setTripartiteAgreementSeq(sequenceGenerator.getNextSequence(PREFIX_TRIPARTITE_AGREEMENT.toString(),
                TABLE_TRIPARTITE_AGREEMENT.toString()));
        entity.setInternPlanSeq(internPlanId);
        entity.setInternStdSeq(internStdId);
        entity.setCorporateSignedYn(Constants.STATE_Y);
        entity.setCorporateSignedDate(new Date());
        entity.setStdSignedYn(Constants.STATE_N);
        entity.setUnivSignedYn(Constants.STATE_N);
        entity.setDelYn(Constants.STATE_N);

        return entity;
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
