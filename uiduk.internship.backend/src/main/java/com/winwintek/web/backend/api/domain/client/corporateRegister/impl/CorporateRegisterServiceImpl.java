package com.winwintek.web.backend.api.domain.client.corporateRegister.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.client.corporateRegister.service.ICorporateRegisterService;
import com.winwintek.web.backend.api.dto.client.corporateRegister.CorporateInfoRegisterRequest;
import com.winwintek.web.backend.api.entity.client.corporateRegister.CorporateInfoRegister;
import com.winwintek.web.backend.api.entity.client.corporateRegister.CorporateRegister;
import com.winwintek.web.backend.api.entity.client.corporateRegister.CorporateUserRegister;
import com.winwintek.web.backend.api.entity.client.corporateRegister.CorporateUserRoleRegister;
import com.winwintek.web.backend.api.repository.client.corporateRegister.CorporateInfoRegisterRepository;
import com.winwintek.web.backend.api.repository.client.corporateRegister.CorporateRegisterRepository;
import com.winwintek.web.backend.api.repository.client.corporateRegister.CorporateUserRegisterRepository;
import com.winwintek.web.backend.api.repository.client.corporateRegister.CorporateUserRoleRegisterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.winwintek.web.backend.api.common.Constants.CORM_APPROVE_STATUS_01;
import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_CORPORATE_MEMBERS;
import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_CORPORATE_MEMBERS_MANAGER;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_CORPORATE_MEMBERS;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_CORPORATE_MEMBERS_MANAGER;

@Service
public class CorporateRegisterServiceImpl implements ICorporateRegisterService {

    @Autowired
    private CorporateRegisterRepository corporateRegisterRepo;

    @Autowired
    private CorporateInfoRegisterRepository corporateInfoRegisterRepo;

    @Autowired
    private CorporateUserRegisterRepository corporateUserRegisterRepo;

    @Autowired
    private CorporateUserRoleRegisterRepository corporateUserRoleRegisterRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Override
    @Transactional
    public String createCorporate(CorporateInfoRegisterRequest request) {
        if (isExistRegister(request.getUserId())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        CorporateUserRegister userRegister = new CorporateUserRegister();
        userRegister.setUserId(request.getUserId());
        userRegister.setUserName(request.getCormNameKr());
        userRegister.setUserDiv(Constants.USER_DIV_OUT);
        userRegister.setUserPw(passwordEncoder.encode(request.getUserPw()));

        corporateUserRegisterRepo.save(userRegister);

        genericRoleCorporate(request.getUserId());

        CorporateInfoRegister corporateInfoRegister = modelMapper.map(request, CorporateInfoRegister.class);
        corporateInfoRegister.setCormSeq(sequenceGenerator.getNextSequence(PREFIX_CORPORATE_MEMBERS.toString(),
                TABLE_CORPORATE_MEMBERS.toString()));
        corporateInfoRegister.setCormStatus(CORM_APPROVE_STATUS_01);
        corporateInfoRegister.setCormUserId(request.getUserId());

        List<CorporateRegister> lstCorporateRegister = request.getLstCorporateReg().stream().map(corp -> {
            CorporateRegister entity = modelMapper.map(corp, CorporateRegister.class);
            entity.setCommSeq(sequenceGenerator.getNextSequence(PREFIX_CORPORATE_MEMBERS_MANAGER.toString(),
                    TABLE_CORPORATE_MEMBERS_MANAGER.toString()));
            entity.setCormSeq(corporateInfoRegister.getCormSeq());
            return entity;
        }).toList();

        corporateInfoRegisterRepo.save(corporateInfoRegister);
        corporateRegisterRepo.saveAll(lstCorporateRegister);

        return corporateInfoRegister.getCormSeq();
    }

    @Override
    public boolean isExistRegister(String userId) {
        boolean isExist = corporateUserRegisterRepo.existsById(userId);
        return isExist;
    }

    private void genericRoleCorporate(String userId) {
        CorporateUserRoleRegister userRole = new CorporateUserRoleRegister();
        userRole.setUserId(userId);
        userRole.setRlId(Constants.ROLE_CORPORATE_MEMBERS);
        userRole.setPublicInfo(Constants.STATE_N);
        corporateUserRoleRegisterRepo.save(userRole);
    }


}
