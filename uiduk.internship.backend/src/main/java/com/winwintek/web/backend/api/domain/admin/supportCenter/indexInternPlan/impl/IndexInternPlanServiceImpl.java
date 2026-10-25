package com.winwintek.web.backend.api.domain.admin.supportCenter.indexInternPlan.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.supportCenter.indexInternPlan.service.IndexInternPlanService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.indexInternPlan.IndexInternPlanListDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.indexInternPlan.IndexInternPlanSearchDTO;
import com.winwintek.web.backend.api.entity.admin.supportCenter.indexInternPlan.IIndexInternPlanResponse;
import com.winwintek.web.backend.api.repository.admin.supportCenter.indexInternPlan.IndexInternPlanRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IndexInternPlanServiceImpl implements IndexInternPlanService {

    @Autowired
    private IndexInternPlanRepository indexInternPlanRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Override
    public Page<IndexInternPlanListDTO> getIndexInternPlanList(IndexInternPlanSearchDTO searchDTO) {
        int page = searchDTO.getPage();
        int size = searchDTO.getSize();
        String sort = searchDTO.getSort();
        Pageable pageable = commonService.buildPageable(page, size, sort);
        Page<IIndexInternPlanResponse> list = indexInternPlanRepository.getListIndexInternPlan(searchDTO,
                Constants.INTERN_PLAN_STATUS_ACCEPTED, Constants.CD_CORM_APPROVAL, Constants.STATE_N, pageable);
        Page<IndexInternPlanListDTO> result = list.map(item -> modelMapper.map(item, IndexInternPlanListDTO.class));

        return result;
    }

}
