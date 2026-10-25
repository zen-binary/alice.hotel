package com.winwintek.web.backend.api.domain.admin.supportCenter.completionMng.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.domain.admin.supportCenter.completionMng.service.CompletionMngService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.completionMng.CompletionStdCountResponseDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.completionMng.CompletionStdMngRequestDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.completionMng.CompletionStdMngResponseDTO;
import com.winwintek.web.backend.api.entity.admin.supportCenter.completionMng.CompletionStdCountResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.completionMng.CompletionStdMngEntity;
import com.winwintek.web.backend.api.entity.admin.supportCenter.completionMng.CompletionStdMngResponse;
import com.winwintek.web.backend.api.repository.admin.supportCenter.completionMng.CompletionStdMngRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CompletionMngServiceImpl implements CompletionMngService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CompletionStdMngRepository completionStdMngRepo;


    @Override
    public Page<CompletionStdMngResponseDTO> getDataPageCompletion(CompletionStdMngRequestDTO dto) {
        Pageable pageable = commonService.buildPageable(dto.getPage(), dto.getSize(), dto.getSort());

        dto.setInternComplete(Constants.CD_STD_INTERN_COMPLETE);
        dto.setInternWaitComplete(Constants.CD_STD_INTERN_WAIT_COMPLETE);
        dto.setInternNotComplete(Constants.CD_STD_INTERN_NOT_COMPLETE);
        dto.setInternCancelBackups(Constants.CD_STD_INTERN_CANCEL_BACKUPS);
        dto.setApproveStatusIntern(Constants.STATUS_APPROVE_02);

        Page<CompletionStdMngResponse> getPageCompletion = completionStdMngRepo.getPageCompletionStd(pageable, dto,
                Constants.STATE_N);
        Page<CompletionStdMngResponseDTO> listDto = getPageCompletion.map(
                (item) -> modelMapper.map(item, CompletionStdMngResponseDTO.class));
        return listDto;
    }

    @Override
    @Transactional
    public boolean waitCompleteIntern(List<String> lstInternStdId) {
        return changeStatusInternStd(lstInternStdId, Constants.CD_STD_INTERN_WAIT_COMPLETE);
    }

    @Override
    @Transactional
    public boolean completeIntern(List<String> lstInternStdId) {
        return changeStatusInternStd(lstInternStdId, Constants.CD_STD_INTERN_COMPLETE);
    }

    @Override
    @Transactional
    public boolean notCompleteIntern(List<String> lstInternStdId) {
        return changeStatusInternStd(lstInternStdId, Constants.CD_STD_INTERN_NOT_COMPLETE);
    }

    @Override
    @Transactional
    public boolean backupsIntern(List<String> lstInternStdId) {
        return changeStatusInternStd(lstInternStdId, Constants.CD_STD_INTERN_CANCEL_BACKUPS);
    }

    @Override
    public CompletionStdCountResponseDTO getCountStatus(CompletionStdMngRequestDTO dto) {
        dto.setInternComplete(Constants.CD_STD_INTERN_COMPLETE);
        dto.setInternWaitComplete(Constants.CD_STD_INTERN_WAIT_COMPLETE);
        dto.setInternNotComplete(Constants.CD_STD_INTERN_NOT_COMPLETE);
        dto.setInternCancelBackups(Constants.CD_STD_INTERN_CANCEL_BACKUPS);
        dto.setApproveStatusIntern(Constants.STATUS_APPROVE_02);
        CompletionStdCountResponse completionCount = completionStdMngRepo.getCountStatusCompletion(dto,
                Constants.STATE_N);
        CompletionStdCountResponseDTO resultCount = modelMapper.map(completionCount,
                CompletionStdCountResponseDTO.class);
        return resultCount;
    }

    private boolean changeStatusInternStd(List<String> lstInternStdId, String internStatusStd) {
        List<CompletionStdMngEntity> lstCompletionStd = new ArrayList<>();
        lstInternStdId.forEach(item -> {
            CompletionStdMngEntity entity = completionStdMngRepo.findById(item)
                    .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));
            entity.setInternshipStatus(internStatusStd);
            lstCompletionStd.add(entity);
        });
        completionStdMngRepo.saveAll(lstCompletionStd);
        return true;
    }

}
