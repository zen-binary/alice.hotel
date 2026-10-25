package com.winwintek.web.backend.api.domain.admin.internPlanCenter.service;

import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterDetailDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterListDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterSearchDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterStsReqDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterStudentDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterStudentListDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCountStsDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCountStsReqDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InternPlanCenterService {
    Page<InternPlanCenterListDto> getListInternPlanCenter(InternPlanCenterSearchDto dto);

    Page<InternPlanCenterStudentListDto> getListInternPlanStudent(InternPlanCenterStudentDto dto);

    InternPlanCountStsDto getStatusStudentInternPlan(InternPlanCountStsReqDto dto);

    boolean deleteInternPlan(List<String> lstIdInternPlan);

    boolean approveInternPlan(InternPlanCenterStsReqDto req);

    boolean rejectApproveInternPlan(InternPlanCenterStsReqDto req);

    boolean cancelApproveInternPlan(InternPlanCenterStsReqDto req);

    boolean deleteInternStd(List<String> lstIdInternStd);

    boolean approveInternStd(List<String> lstIdInternStd);

    boolean notApproveInternStd(List<String> lstIdInternStd);

    boolean cancelApproveInternStd(List<String> lstIdInternStd);

    InternPlanCenterDetailDto detailInternPlan(String data);

}
