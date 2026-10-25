package com.winwintek.web.backend.api.domain.client.participationCertificate.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.client.participationCertificate.service.ParticipationCertificateService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.client.participationCertificate.IParticipationCertificateCusDTO;
import com.winwintek.web.backend.api.dto.client.participationCertificate.ParticipationCertificateDTO;
import com.winwintek.web.backend.api.repository.client.participationCertificate.ParticipationCertificateRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipationCertificateServiceImpl implements ParticipationCertificateService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ParticipationCertificateRepository participationCertificateRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Override
    public ParticipationCertificateDTO detail(String id) {
        String userId = commonService.getFullUserInfo().getUserId();
        IParticipationCertificateCusDTO iParticipationCertificateCusDTO = participationCertificateRepository.getParticipationCertificateDetail(
                id, Constants.STATE_N, userId);
        ParticipationCertificateDTO participationCertificateDTO = modelMapper.map(iParticipationCertificateCusDTO,
                ParticipationCertificateDTO.class);
        return participationCertificateDTO;
    }

}
