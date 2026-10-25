package com.winwintek.web.backend.api.domain.client.student.signedAgreement.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.file.service.FileMngService;
import com.winwintek.web.backend.api.domain.client.student.signedAgreement.service.ISignedAgreementService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.client.student.signedAgreement.SignedAgreementResDTO;
import com.winwintek.web.backend.api.dto.common.fileMng.FileDto;
import com.winwintek.web.backend.api.dto.common.fileMng.FileUploadedInfoDto;
import com.winwintek.web.backend.api.entity.client.student.signedAgreement.ISignedAgreementOutput;
import com.winwintek.web.backend.api.entity.client.student.signedAgreement.TripartiteAgreementStdEntity;
import com.winwintek.web.backend.api.repository.client.student.signedAgreement.SignedAgreementRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.winwintek.web.backend.api.common.Constants.STATE_N;
import static com.winwintek.web.backend.api.common.Constants.STATE_Y;

@Service
public class SignedAgreementServiceImpl implements ISignedAgreementService {

    @Autowired
    private SignedAgreementRepository signedAgreementRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private FileMngService fileMngService;

    @Override
    public SignedAgreementResDTO getSignedAgreement(String internStdSeq) {

        String stdId = commonService.getFullUserInfo().getUserId();

        ISignedAgreementOutput response = signedAgreementRepository.getSignedAgreement(stdId, internStdSeq, STATE_N,
                STATE_Y);

        String fimFileCategory = Constants.TRIPARTITE_CORM_SIGN_CATEGORY;
        String fimSectionName = Constants.TRIPARTITE_CORM_SIGN_SECTION;

        SignedAgreementResDTO output = modelMapper.map(response, SignedAgreementResDTO.class);
        String urlFile = getUrlFile(output.getCormSeq(), fimFileCategory, fimSectionName);
        output.setUrlFile(urlFile);
        return output;
    }

    @Override
    @Transactional
    public boolean studentSignAgreement(String id) {
        Optional<TripartiteAgreementStdEntity> optionalEntity = signedAgreementRepository.findByTripartiteAgreementSeqAndDelYn(
                id, STATE_N);
        if (optionalEntity.isPresent()) {
            TripartiteAgreementStdEntity currentEntity = optionalEntity.get();

            currentEntity.setStdSignedYn(STATE_Y);
            currentEntity.setStdSignedDate(Instant.now());

            signedAgreementRepository.save(currentEntity);

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
