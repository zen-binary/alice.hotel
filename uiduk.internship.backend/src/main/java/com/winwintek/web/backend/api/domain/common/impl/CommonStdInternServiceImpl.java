package com.winwintek.web.backend.api.domain.common.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.domain.common.service.CommonStdInternService;
import com.winwintek.web.backend.api.dto.common.stdIntern.StudentInternAdminDTO;
import com.winwintek.web.backend.api.dto.common.stdIntern.StudentInternClientDTO;
import com.winwintek.web.backend.api.entity.common.stdIntern.IStdInternAdminResponse;
import com.winwintek.web.backend.api.entity.common.stdIntern.IStdInternClientResponse;
import com.winwintek.web.backend.api.repository.common.stdIntern.StudentInternRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonStdInternServiceImpl implements CommonStdInternService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentInternRepository studentInternRepository;


    @Override
    public List<StudentInternAdminDTO> getListStudentAdmin(String internPlanId) {
        List<IStdInternAdminResponse> lstResult = studentInternRepository.getListStudentReportAdmin(internPlanId,
                Constants.INTERN_STD_APPROVE_STATUS, Constants.STATE_N);

        List<StudentInternAdminDTO> lstResultDTO = lstResult.stream()
                .map(item -> modelMapper.map(item, StudentInternAdminDTO.class)).toList();

        return lstResultDTO;
    }

    @Override
    public List<StudentInternClientDTO> getListStudentClient(String internPlanId) {
        List<IStdInternClientResponse> lstResult = studentInternRepository.getListStudentReportClient(internPlanId,
                Constants.INTERN_STD_APPROVE_STATUS, Constants.STATE_N);

        List<StudentInternClientDTO> lstResultDTO = lstResult.stream()
                .map(item -> modelMapper.map(item, StudentInternClientDTO.class)).toList();

        return lstResultDTO;
    }

}
