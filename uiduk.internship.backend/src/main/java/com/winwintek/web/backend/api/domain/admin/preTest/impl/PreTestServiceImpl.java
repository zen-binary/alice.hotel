package com.winwintek.web.backend.api.domain.admin.preTest.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.preTest.service.PreTestService;
import com.winwintek.web.backend.api.dto.admin.preTest.*;
import com.winwintek.web.backend.api.entity.admin.preTest.ICorporateMemberInfoCusDTO;
import com.winwintek.web.backend.api.entity.admin.preTest.IPreTestCategoryCusDTO;
import com.winwintek.web.backend.api.entity.admin.preTest.IPreTestDetailCusDTO;
import com.winwintek.web.backend.api.entity.admin.preTest.IPreTestDetailSelectCusDTO;
import com.winwintek.web.backend.api.repository.admin.preTest.PreTestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PreTestServiceImpl implements PreTestService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PreTestRepository preTestRepository;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Override
    public CorporateMemberReqDTO getCorPreTestList(CorporatePreTestSeqDTO corporatePreTestSeqDTO) {

        List<IPreTestDetailCusDTO> preTestDetail = preTestRepository.selectPreTestDetail(Constants.STATE_N);
        List<PreTestDetailResDTO> preTestDetailDTO = preTestDetail.stream()
                .map(detail -> modelMapper.map(detail, PreTestDetailResDTO.class)).toList();

        List<IPreTestCategoryCusDTO> preTestCategory = preTestRepository.selectPreTestCategory(Constants.STATE_N);
        List<PreTestCategoryResDTO> preTestCategoryDTO = preTestCategory.stream()
                .map(category -> modelMapper.map(category, PreTestCategoryResDTO.class)).toList();
        List<PreTestDetailSelectResDTO> detailSelectDTO = new ArrayList<>();
        CorporateMemberReqDTO memberInfoDTO = new CorporateMemberReqDTO();

        ICorporateMemberInfoCusDTO memberInfo = preTestRepository.selectmemberInfo(corporatePreTestSeqDTO,
                Constants.STATE_N);

        memberInfoDTO = modelMapper.map(memberInfo, CorporateMemberReqDTO.class);

        if (! StringUtils.equals(corporatePreTestSeqDTO.getInternPlanSeq(), null)) {

            List<IPreTestDetailSelectCusDTO> detailSelect = preTestRepository.selectDetailSelect(corporatePreTestSeqDTO,
                    Constants.STATE_N);
            detailSelectDTO = detailSelect.stream()
                    .map(itemSelect -> modelMapper.map(itemSelect, PreTestDetailSelectResDTO.class)).toList();

            memberInfoDTO.setPreTestDetailSelectResDTOList(detailSelectDTO);
        }

        for (PreTestCategoryResDTO category : preTestCategoryDTO) {
            List<PreTestDetailResDTO> listDetail = new ArrayList<>();
            for (PreTestDetailResDTO detail : preTestDetailDTO) {
                if (StringUtils.equals(detail.getPreTestCategorySeq(), category.getPreTestCategorySeq())) {
                    listDetail.add(detail);
                }
                for (PreTestDetailSelectResDTO selectDTO : detailSelectDTO) {
                    if (StringUtils.equals(detail.getPreTestDetailSeq(), selectDTO.getPreTestDetailSeq())) {
                        detail.setCheckYn(Constants.STATE_Y);
                        detail.setContentDetail(selectDTO.getAddContent());
                    }
                }
            }
            category.setPreTestDetailResDTOList(listDetail);
        }

        memberInfoDTO.setPreTestCategoryResDTOList(preTestCategoryDTO);

        return memberInfoDTO;
    }

}
