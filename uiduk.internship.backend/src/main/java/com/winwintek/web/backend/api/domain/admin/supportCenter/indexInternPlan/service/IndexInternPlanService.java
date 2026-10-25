package com.winwintek.web.backend.api.domain.admin.supportCenter.indexInternPlan.service;


import com.winwintek.web.backend.api.dto.admin.supportCenter.indexInternPlan.IndexInternPlanListDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.indexInternPlan.IndexInternPlanSearchDTO;
import org.springframework.data.domain.Page;

public interface IndexInternPlanService {

    Page<IndexInternPlanListDTO> getIndexInternPlanList(IndexInternPlanSearchDTO searchDTO);

}
