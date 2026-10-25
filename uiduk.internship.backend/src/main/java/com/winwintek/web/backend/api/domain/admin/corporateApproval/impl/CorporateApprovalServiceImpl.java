package com.winwintek.web.backend.api.domain.admin.corporateApproval.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.domain.admin.corporateApproval.service.ICorporateApprovalService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.admin.corporateApproval.CorporateApprovalDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.corporateApproval.CorporateApprovalManagerDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.corporateApproval.CorporateApprovalResDTO;
import com.winwintek.web.backend.api.dto.admin.corporateApproval.CorporateApprovalSearchDTO;
import com.winwintek.web.backend.api.entity.admin.corporateApproval.CorporateApproval;
import com.winwintek.web.backend.api.entity.admin.corporateApproval.CorporateManagerApproval;
import com.winwintek.web.backend.api.entity.admin.corporateApproval.ICorporateApprovalResponse;
import com.winwintek.web.backend.api.repository.admin.corporateApproval.CorporateApprovalRepository;
import com.winwintek.web.backend.api.repository.admin.corporateApproval.CorporateManagerApprovalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CorporateApprovalServiceImpl implements ICorporateApprovalService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CorporateApprovalRepository corporateApprovalRepo;

    @Autowired
    private CorporateManagerApprovalRepository corporateManagerApprovalRepo;

    @Override
    public Page<CorporateApprovalResDTO> getListCorporateApproval(CorporateApprovalSearchDTO dto) {
        Pageable pageable = commonService.buildPageable(dto.getPage(), dto.getSize(), dto.getSort());
        Page<ICorporateApprovalResponse> getPageCorporateApproval = corporateApprovalRepo.getListCorporateApproval(
                pageable, dto, Constants.STATE_N, Constants.CD_CORM_WAIT_APPROVAL, Constants.CD_CORM_APPROVAL);
        Page<CorporateApprovalResDTO> resultLst = getPageCorporateApproval.map(
                item -> modelMapper.map(item, CorporateApprovalResDTO.class));
        return resultLst;
    }

    @Override
    @Transactional
    public boolean changeApprovalCorporate(List<String> data) {
        List<CorporateApproval> lstCorp = new ArrayList<>();
        if (data.isEmpty()) {
            return false;
        }
        data.forEach(item -> {
            CorporateApproval entity = corporateApprovalRepo.findById(item)
                    .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));
            entity.setCormStatus(Constants.CD_CORM_APPROVAL);
            entity.setCormConfirmDate(new Date());
            lstCorp.add(entity);
        });
        corporateApprovalRepo.saveAll(lstCorp);
        return true;
    }

    @Override
    public CorporateApprovalDetailResDTO getDetailCorporate(String cormSeq) {
        CorporateApproval corporateApproval = corporateApprovalRepo.findById(cormSeq)
                .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));
        List<CorporateManagerApproval> lstCorporateManager = corporateManagerApprovalRepo.getLstCorporateManager(
                corporateApproval.getCormSeq(), Constants.STATE_N);
        CorporateApprovalDetailResDTO result = modelMapper.map(corporateApproval, CorporateApprovalDetailResDTO.class);
        List<CorporateApprovalManagerDetailResDTO> lstApprovalManagerDTO = lstCorporateManager.stream()
                .map(item -> modelMapper.map(item, CorporateApprovalManagerDetailResDTO.class)).toList();
        result.setLstCorporateManager(lstApprovalManagerDTO);
        return result;
    }

    @Override
    public List<CorporateApprovalResDTO> getALLDataExcelCorporateApproval(CorporateApprovalSearchDTO dto) {
        List<ICorporateApprovalResponse> dataExcelCorporateApproval =
        corporateApprovalRepo.getAllDataExcelCorporateApproval(dto, Constants.STATE_N,
                Constants.CD_CORM_WAIT_APPROVAL, Constants.CD_CORM_APPROVAL);
        List<CorporateApprovalResDTO> resultLst = dataExcelCorporateApproval.stream()
                .map(item -> modelMapper.map(item, CorporateApprovalResDTO.class)).toList();
        return resultLst;
    }

}
