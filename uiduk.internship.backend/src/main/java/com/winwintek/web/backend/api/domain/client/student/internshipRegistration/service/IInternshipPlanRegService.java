package com.winwintek.web.backend.api.domain.client.student.internshipRegistration.service;

import com.winwintek.web.backend.api.dto.client.student.internPlanDetail.InternPlanDetailResDTO;
import com.winwintek.web.backend.api.dto.client.student.internshipRegistration.DetailInternPlanRegFormResDTO;
import com.winwintek.web.backend.api.dto.client.student.internshipRegistration.InternshipRegReqDTO;
import com.winwintek.web.backend.api.dto.client.student.internshipRegistration.InternshipRegResDTO;

import java.util.List;

public interface IInternshipPlanRegService {
    List<InternshipRegResDTO> getInternPlanRegList(InternshipRegReqDTO req);

    List<String> getListYear();

    DetailInternPlanRegFormResDTO getRegForm(String id);

}
