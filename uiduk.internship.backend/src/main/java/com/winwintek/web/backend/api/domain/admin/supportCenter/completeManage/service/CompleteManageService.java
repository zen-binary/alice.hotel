package com.winwintek.web.backend.api.domain.admin.supportCenter.completeManage.service;

import com.winwintek.web.backend.api.dto.admin.supportCenter.completeManage.CompleteGetByInternPlanId;
import com.winwintek.web.backend.api.dto.admin.supportCenter.completeManage.CompleteManageDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompleteManageService {
    Page<CompleteManageDTO> getListStudent(CompleteGetByInternPlanId dto);

    Boolean complete(List<String> values);

    Boolean cancelComplete(List<String> values);

    Boolean unFinish(List<String> values);

    Boolean reserve(List<String> values);

    Boolean cancelNotComplete(List<String> values);

    Boolean cancelReservation(List<String> values);

}
