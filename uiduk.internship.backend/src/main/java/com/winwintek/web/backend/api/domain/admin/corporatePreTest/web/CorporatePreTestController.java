package com.winwintek.web.backend.api.domain.admin.corporatePreTest.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.corporatePreTest.service.CorporatePreTestService;
import com.winwintek.web.backend.api.dto.admin.corporatePreTest.CorporateMemberInfoDTO;
import com.winwintek.web.backend.api.dto.admin.corporatePreTest.CorporatePreTestIdDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/c/corparate-pre-test")
@RequiredArgsConstructor
public class CorporatePreTestController {

    @Autowired
    private CorporatePreTestService preTestService;

    @PostMapping("/info")
    public ApiResponse<CorporateMemberInfoDTO> getStoryById(@RequestBody CorporatePreTestIdDTO corporatePreTestIdDTO) {
        return new ApiResponse<>(ApiStatus.SUCCESS, preTestService.getCorPreTestList(corporatePreTestIdDTO));
    }

    @PostMapping("/detail")
    public ApiResponse<CorporateMemberInfoDTO> getDetailCorporatePreTest(@RequestBody String internPlanSeq) {
        return new ApiResponse<>(ApiStatus.SUCCESS, preTestService.getDetailCorporatePreTest(internPlanSeq));
    }

    @PostMapping("/create")
    public ApiResponse<Boolean> insertCorporatePreTest(@RequestBody CorporateMemberInfoDTO memberInfoDTO) {
        return new ApiResponse<>(ApiStatus.CREATED, preTestService.insertCorporatePreTest(memberInfoDTO));
    }

}
