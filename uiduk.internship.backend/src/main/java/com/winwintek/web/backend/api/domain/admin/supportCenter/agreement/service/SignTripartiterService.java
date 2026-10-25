package com.winwintek.web.backend.api.domain.admin.supportCenter.agreement.service;

import com.winwintek.web.backend.api.dto.admin.supportCenter.agreement.SignTripartiteDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.agreement.StudentInternSupportCenterDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.agreement.UpdateListAppendSupportCenterDTO;

import java.util.List;

public interface SignTripartiterService {

    SignTripartiteDTO getListSignSupportCenter(String internPlanId);

    List<StudentInternSupportCenterDTO> getListStudentSupportCenter(String internPlanId);

    Boolean appendAllSupportCenter(UpdateListAppendSupportCenterDTO updateListAppendDTO);

}
