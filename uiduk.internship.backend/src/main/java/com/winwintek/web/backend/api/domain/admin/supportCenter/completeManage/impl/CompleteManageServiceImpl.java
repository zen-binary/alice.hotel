package com.winwintek.web.backend.api.domain.admin.supportCenter.completeManage.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.domain.admin.supportCenter.completeManage.service.CompleteManageService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.completeManage.CompleteGetByInternPlanId;
import com.winwintek.web.backend.api.dto.admin.supportCenter.completeManage.CompleteManageDTO;
import com.winwintek.web.backend.api.entity.admin.supportCenter.completeManage.ICompleteManageReponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.completeManage.InternStdEntity;
import com.winwintek.web.backend.api.repository.admin.supportCenter.completeManage.CompleteManageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompleteManageServiceImpl implements CompleteManageService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CompleteManageRepository completeManageRepository;

    @Autowired
    private CommonService commonService;

    @Override
    public Page<CompleteManageDTO> getListStudent(CompleteGetByInternPlanId dto) {
        int page = dto.getPage();
        int size = dto.getSize();
        String sort = dto.getSort();
        Pageable pageable = commonService.buildPageable(page, size, sort);

        Page<ICompleteManageReponse> lstResult = completeManageRepository.getListInfoStudent(dto.getInternPlanId(), Constants.INTERN_STD_APPROVE_STATUS,
                pageable, Constants.STATE_N);
           Page<CompleteManageDTO> lstResultDto = lstResult.map(
                item -> modelMapper.map(item, CompleteManageDTO.class)
        );

        return lstResultDto;
    }

    @Override
    public Boolean complete(List<String> values) {
        return updateApprovalStatus(values, Constants.COMPLETE);
    }

    @Override
    public Boolean cancelComplete(List<String> values) {
        return updateApprovalStatus(values, Constants.WAIT_COMPLETE);
    }

    @Override
    public Boolean unFinish(List<String> values) {
        return updateApprovalStatus(values, Constants.UNFINISHED);
    }

    @Override
    public Boolean reserve(List<String> values) {
        return updateApprovalStatus(values, Constants.RESERVE);
    }

    @Override
    public Boolean cancelNotComplete(List<String> values) {
        return updateApprovalStatus(values, Constants.WAIT_COMPLETE);
    }

    @Override
    public Boolean cancelReservation(List<String> values) {
        return updateApprovalStatus(values, Constants.WAIT_COMPLETE);
    }

    public Boolean updateApprovalStatus(List<String> values, String status) {
        List<InternStdEntity> listEntity = new ArrayList<>();
        for (String id : values) {
            Optional<InternStdEntity> entity = completeManageRepository.findById(id);
            if (! entity.isPresent()) {
                throw new CustomException(ApiStatus.NOT_FOUND);
            }
            entity.get().setInternshipStatus(status);
            listEntity.add(entity.get());
        }
        completeManageRepository.saveAll(listEntity);
        return true;
    }


}
