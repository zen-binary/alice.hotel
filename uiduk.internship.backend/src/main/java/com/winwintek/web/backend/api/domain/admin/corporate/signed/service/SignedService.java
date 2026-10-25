package com.winwintek.web.backend.api.domain.admin.corporate.signed.service;

import com.winwintek.web.backend.api.dto.admin.corporate.signed.SignedDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.signed.StudentInternCorporate;
import com.winwintek.web.backend.api.dto.admin.corporate.signed.TripartiteAgreeReqDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.signed.UpdateListAppendDTO;

import java.util.List;

public interface SignedService {

    SignedDTO getListSign(String internPlanId);

    List<StudentInternCorporate> getListStudentCorporate(String internPlanId);

    Boolean appendAll(UpdateListAppendDTO updateListAppendDTO);

    List<StudentInternCorporate> createTripartiteAgree(TripartiteAgreeReqDTO dto);

}
