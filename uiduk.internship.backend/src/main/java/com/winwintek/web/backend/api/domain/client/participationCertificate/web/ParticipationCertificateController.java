package com.winwintek.web.backend.api.domain.client.participationCertificate.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.participationCertificate.service.ParticipationCertificateService;
import com.winwintek.web.backend.api.dto.client.participationCertificate.ParticipationCertificateDTO;
import com.winwintek.web.backend.api.dto.client.participationCertificate.ParticipationCertificateDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/c/participation")
@RequiredArgsConstructor
public class ParticipationCertificateController {

    private final ParticipationCertificateService participationCertificateService;

    @PostMapping ("/detail")
    public ApiResponse<ParticipationCertificateDTO> detail(@RequestBody ParticipationCertificateDetailDTO request) {
        ParticipationCertificateDTO result = participationCertificateService.detail(request.getInternPlanSeq());
        return new ApiResponse<ParticipationCertificateDTO>(ApiStatus.SUCCESS, result);
    }

}
