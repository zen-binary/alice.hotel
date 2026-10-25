package com.winwintek.web.backend.api.domain.admin.internPlan.service;

import com.winwintek.web.backend.api.dto.admin.corporateManager.CorporateManagerListDto;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanCormMemberInfoDTO;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanDetailDto;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanExistingDto;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanListDto;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanRequestDto;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanSearchDto;
import com.winwintek.web.backend.api.dto.admin.staffIntern.StaffInternDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InternPlanService {

    Page<InternPlanListDto> getListInternPlan(InternPlanSearchDto dto);

    String create(InternPlanRequestDto dto);

    InternPlanDetailDto detail(String data);

    List<InternPlanExistingDto> getInternPlanExisting();

    List<InternPlanExistingDto> getInternPlanExistingUpdate(String internPlanSeq);

    Integer executionCount(String data);

    List<CorporateManagerListDto> getCorporateManager();

    List<StaffInternDto>getListStaff();

    Boolean delete(List<String>data);

    InternPlanCormMemberInfoDTO getInternPlanCormMemberInfo();

}
