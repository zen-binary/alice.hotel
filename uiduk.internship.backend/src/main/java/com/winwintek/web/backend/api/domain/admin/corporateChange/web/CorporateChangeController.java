package com.winwintek.web.backend.api.domain.admin.corporateChange.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.corporateChange.service.ICorporateChangeService;
import com.winwintek.web.backend.api.dto.admin.corporateChange.CorporateInfoChangeRequest;
import com.winwintek.web.backend.api.dto.admin.corporateChange.CorporateInfoChangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/a/c/corporate-change")
public class CorporateChangeController {

    @Autowired
    private ICorporateChangeService corporateChangeService;

    @PostMapping ()
    public ApiResponse<CorporateInfoChangeResponse> detailCoporate() {
        CorporateInfoChangeResponse data = corporateChangeService.detailCorporate();
        return new ApiResponse<>(ApiStatus.SUCCESS, data);
    }

    @PutMapping ("/update")
    public ApiResponse<String> updateCoporate(@RequestBody CorporateInfoChangeRequest request) {
        String data = corporateChangeService.updateCorporate(request);
        return new ApiResponse<>(ApiStatus.SUCCESS, data);
    }
}
