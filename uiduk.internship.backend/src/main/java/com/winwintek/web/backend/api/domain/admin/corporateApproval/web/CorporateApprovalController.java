package com.winwintek.web.backend.api.domain.admin.corporateApproval.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.corporateApproval.service.ICorporateApprovalService;
import com.winwintek.web.backend.api.domain.client.student.stdSurveyAns.service.IStdSurveyAnsService;
import com.winwintek.web.backend.api.dto.admin.corporateApproval.CorporateApprovalDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.corporateApproval.CorporateApprovalResDTO;
import com.winwintek.web.backend.api.dto.admin.corporateApproval.CorporateApprovalSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/a/corporate-approval")
public class CorporateApprovalController {

    @Autowired
    private ICorporateApprovalService corporateApprovalService;

    @PostMapping()
    public ApiResponse<Page<CorporateApprovalResDTO>> getPage(@RequestBody CorporateApprovalSearchDTO dto) {
        Page<CorporateApprovalResDTO> listDtoPage = corporateApprovalService.getListCorporateApproval(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, listDtoPage);
    }

    @PutMapping ("/approval")
    public ApiResponse<Boolean> corporateApproval(@RequestBody List<String> data) {
        boolean result = corporateApprovalService.changeApprovalCorporate(data);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/detail")
    public ApiResponse<CorporateApprovalDetailResDTO> detailCorporateApproval(@RequestBody String cormSeq) {
        CorporateApprovalDetailResDTO result = corporateApprovalService.getDetailCorporate(cormSeq);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping("/export-excel")
    public ApiResponse<List<CorporateApprovalResDTO>> getDataExcel(@RequestBody CorporateApprovalSearchDTO dto) {
        List<CorporateApprovalResDTO> listExcel = corporateApprovalService.getALLDataExcelCorporateApproval(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, listExcel);
    }
}
