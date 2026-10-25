package com.winwintek.web.backend.api.domain.common.service;

import com.winwintek.web.backend.api.dto.common.stdIntern.StudentInternAdminDTO;
import com.winwintek.web.backend.api.dto.common.stdIntern.StudentInternClientDTO;

import java.util.List;

public interface CommonStdInternService {

    List<StudentInternAdminDTO> getListStudentAdmin(String internPlanId);

    List<StudentInternClientDTO> getListStudentClient(String internPlanId);

}
