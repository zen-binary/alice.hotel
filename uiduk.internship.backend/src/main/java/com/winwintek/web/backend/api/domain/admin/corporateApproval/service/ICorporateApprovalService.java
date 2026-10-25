package com.winwintek.web.backend.api.domain.admin.corporateApproval.service;

import com.winwintek.web.backend.api.dto.admin.corporateApproval.CorporateApprovalDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.corporateApproval.CorporateApprovalResDTO;
import com.winwintek.web.backend.api.dto.admin.corporateApproval.CorporateApprovalSearchDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICorporateApprovalService {

    Page<CorporateApprovalResDTO> getListCorporateApproval(CorporateApprovalSearchDTO dto);

    boolean changeApprovalCorporate(List<String> data);

    CorporateApprovalDetailResDTO getDetailCorporate(String cormSeq);

    List<CorporateApprovalResDTO> getALLDataExcelCorporateApproval(CorporateApprovalSearchDTO dto);
}
