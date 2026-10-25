package com.winwintek.web.backend.api.domain.admin.corporate.operateInternMng.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.domain.admin.corporate.operateInternMng.service.OperateInternCorpService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.admin.cmmCorporateMember.CmmCorporateMembersDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.operateInternMng.OperateInternCorpResDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.operateInternMng.OperateInternCorpSearchDTO;
import com.winwintek.web.backend.api.entity.admin.corporate.operateInternMng.IOperateInternCorpResponse;
import com.winwintek.web.backend.api.repository.admin.corporate.operateInternMng.OperateInternCorpRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OperateInternCorpServiceImpl implements OperateInternCorpService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private OperateInternCorpRepository operateInternCorpRepository;

    @Override
    public Page<OperateInternCorpResDTO> findListOperateInternCorp(OperateInternCorpSearchDTO searchDTO) {
        int page = searchDTO.getPage();
        int size = searchDTO.getSize();
        String sort = searchDTO.getSort();
        Pageable pageable = commonService.buildPageable(page, size, sort);

        CmmCorporateMembersDTO cmmCorporateMembersDTO = commonService.getCorporateMembersInfo();
        searchDTO.setCorpApproveStatus(Constants.CD_CORM_APPROVAL);
        searchDTO.setPlanApproveStatus(Constants.CD_INTERN_PLAN_APPROVAL);
        searchDTO.setDelYn(Constants.STATE_N);

        Page<IOperateInternCorpResponse> lstResult = operateInternCorpRepository.searchOperateIntern(searchDTO,
                cmmCorporateMembersDTO.getCormSeq(), pageable);

        Page<OperateInternCorpResDTO> lstResultDTO = lstResult.map(
                item -> modelMapper.map(item, OperateInternCorpResDTO.class));

        return lstResultDTO;
    }

}
