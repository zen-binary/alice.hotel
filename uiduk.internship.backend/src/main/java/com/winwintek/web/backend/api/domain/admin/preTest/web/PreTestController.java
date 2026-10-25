package com.winwintek.web.backend.api.domain.admin.preTest.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.preTest.service.PreTestService;
import com.winwintek.web.backend.api.dto.admin.preTest.CorporateMemberReqDTO;
import com.winwintek.web.backend.api.dto.admin.preTest.CorporatePreTestSeqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/a/pre-test")
@RequiredArgsConstructor

public class PreTestController {

    @Autowired
    private PreTestService preTestService;

    @PostMapping ("/detail")
    public ApiResponse<CorporateMemberReqDTO> getStoryById(@RequestBody CorporatePreTestSeqDTO corporatePreTestSeqDTO) {
        return new ApiResponse<>(ApiStatus.SUCCESS, preTestService.getCorPreTestList(corporatePreTestSeqDTO));
    }

}
