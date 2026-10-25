package com.winwintek.web.backend.api.domain.admin.corporateChange.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.domain.admin.corporateChange.service.ICorporateChangeService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.admin.corporateChange.CorporateChangeResponse;
import com.winwintek.web.backend.api.dto.admin.corporateChange.CorporateInfoChangeRequest;
import com.winwintek.web.backend.api.dto.admin.corporateChange.CorporateInfoChangeResponse;
import com.winwintek.web.backend.api.entity.admin.corporateChange.CorporateChange;
import com.winwintek.web.backend.api.entity.admin.corporateChange.CorporateInfoChange;
import com.winwintek.web.backend.api.entity.admin.corporateChange.CorporateUserChange;
import com.winwintek.web.backend.api.repository.admin.corporateChange.CorporateChangeRepository;
import com.winwintek.web.backend.api.repository.admin.corporateChange.CorporateInfoChangeRepository;
import com.winwintek.web.backend.api.repository.admin.corporateChange.CorporateUserChangeRepository;
import org.springframework.util.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CorporateChangeServiceImpl implements ICorporateChangeService {

    @Autowired
    private CorporateChangeRepository corporateChangeRepo;

    @Autowired
    private CorporateInfoChangeRepository corporateInfoChangeRepo;

    @Autowired
    private CorporateUserChangeRepository corporateUserChangeRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public CorporateInfoChangeResponse detailCorporate() {
        String userId = commonService.getCorporateMembersInfo().getCormUserId();
        CorporateInfoChange corporateInfoChange = corporateInfoChangeRepo.corporateInfoDetail(userId,
                Constants.STATE_N).orElseThrow(() -> new CustomException(
                ApiStatus.BAD_REQUEST_VALID));
        List<CorporateChange> lstCorporateChange =
                corporateChangeRepo.getLstCorporate(corporateInfoChange.getCormSeq(), Constants.STATE_N);
        CorporateInfoChangeResponse result = modelMapper.map(corporateInfoChange, CorporateInfoChangeResponse.class);
        List<CorporateChangeResponse> lstCorporateChangeDTO =
                lstCorporateChange.stream().map(item -> modelMapper.map(item,CorporateChangeResponse.class)).toList();
        result.setLstCorporateChange(lstCorporateChangeDTO);
        return result;
    }

    @Override
    @Transactional
    public String updateCorporate(CorporateInfoChangeRequest request) {
        String userId = commonService.getFullUserInfo().getUserId();
        CorporateInfoChange corporateInfoChange = corporateInfoChangeRepo.corporateInfoDetail(userId,
                Constants.STATE_N).orElseThrow(() -> new CustomException(
                ApiStatus.BAD_REQUEST_VALID));
        modelMapper.map(request, corporateInfoChange);
        CorporateUserChange userChange = corporateUserChangeRepo.findById(userId).orElseThrow(() -> new CustomException(
                ApiStatus.BAD_REQUEST_VALID));
        if(StringUtils.hasText(request.getUserPw())){
            userChange.setUserPw(passwordEncoder.encode(request.getUserPw()));
        }
        userChange.setUserName(request.getCormNameKr());
        corporateUserChangeRepo.save(userChange);

        List<CorporateChange> lstCorporateChange = request.getLstCorporateChange().stream().map(item -> {
            CorporateChange entity = corporateChangeRepo.findById(item.getCommSeq()).orElseThrow(() -> new CustomException(
                    ApiStatus.BAD_REQUEST_VALID));
            modelMapper.map(item,entity);
            return entity;
        }).toList();

        corporateChangeRepo.saveAll(lstCorporateChange);
        corporateInfoChangeRepo.save(corporateInfoChange);

        String cormId = corporateInfoChange.getCormSeq();
        return cormId;
    }

}
