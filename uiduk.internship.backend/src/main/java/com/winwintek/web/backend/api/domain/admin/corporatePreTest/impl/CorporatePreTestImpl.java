package com.winwintek.web.backend.api.domain.admin.corporatePreTest.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.corporatePreTest.service.CorporatePreTestService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.admin.cmmCorporateMember.CmmCorporateMembersDTO;
import com.winwintek.web.backend.api.dto.admin.corporatePreTest.CorporateMemberInfoDTO;
import com.winwintek.web.backend.api.dto.admin.corporatePreTest.CorporatePreTestIdDTO;
import com.winwintek.web.backend.api.dto.admin.corporatePreTest.PreTestCategoryDTO;
import com.winwintek.web.backend.api.dto.admin.corporatePreTest.PreTestDetailDTO;
import com.winwintek.web.backend.api.dto.admin.corporatePreTest.PreTestDetailSelectDTO;
import com.winwintek.web.backend.api.entity.admin.corporatePreTest.CorporatePreTestEntity;
import com.winwintek.web.backend.api.entity.admin.corporatePreTest.ICorporateMemberInfoDTO;
import com.winwintek.web.backend.api.entity.admin.corporatePreTest.IPreTestCategoryDTO;
import com.winwintek.web.backend.api.entity.admin.corporatePreTest.IPreTestDetailDTO;
import com.winwintek.web.backend.api.entity.admin.corporatePreTest.IPreTestDetailSelectDTO;
import com.winwintek.web.backend.api.entity.admin.corporatePreTest.PreTestAnsEntity;
import com.winwintek.web.backend.api.repository.admin.corporatePreTest.ICorporatePreTestRepository;
import com.winwintek.web.backend.api.repository.admin.corporatePreTest.IPreTestAnsRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_CORPORATE_PRE_TEST;
import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_PRE_TEST_ANS;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_CORPORATE_PRE_TEST;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_PRE_TEST_ANS;

@Service
public class CorporatePreTestImpl implements CorporatePreTestService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ICorporatePreTestRepository preTestRepository;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private IPreTestAnsRepository preTestAnsRepository;

    @Override
    public CorporateMemberInfoDTO getCorPreTestList(CorporatePreTestIdDTO corporatePreTestIdDTO) {

        List<IPreTestDetailDTO> preTestDetail = preTestRepository.selectPreTestDetail(Constants.STATE_N);
        List<PreTestDetailDTO> preTestDetailDTO = preTestDetail.stream()
                .map(detail -> modelMapper.map(detail, PreTestDetailDTO.class)).toList();

        List<IPreTestCategoryDTO> preTestCategory = preTestRepository.selectPreTestCategory(Constants.STATE_N);
        List<PreTestCategoryDTO> preTestCategoryDTO = preTestCategory.stream()
                .map(category -> modelMapper.map(category, PreTestCategoryDTO.class)).toList();

        List<PreTestDetailSelectDTO> detailSelectDTO = new ArrayList<>();
        CorporateMemberInfoDTO memberInfoDTO = new CorporateMemberInfoDTO();

        ICorporateMemberInfoDTO memberInfo = preTestRepository.selectmemberInfo(corporatePreTestIdDTO,
                Constants.STATE_N);
        memberInfoDTO = modelMapper.map(memberInfo, CorporateMemberInfoDTO.class);

        if (! StringUtils.equals(corporatePreTestIdDTO.getCorporatePreTestSeq(), null)) {

            List<IPreTestDetailSelectDTO> detailSelect = preTestRepository.selectDetailSelect(corporatePreTestIdDTO,
                    Constants.STATE_N);
            detailSelectDTO = detailSelect.stream()
                    .map(itemSelect -> modelMapper.map(itemSelect, PreTestDetailSelectDTO.class)).toList();

            memberInfoDTO.setPreTestDetailSelectDTOList(detailSelectDTO);
        }


        for (PreTestCategoryDTO category : preTestCategoryDTO) {
            List<PreTestDetailDTO> listDetail = new ArrayList<>();
            for (PreTestDetailDTO detail : preTestDetailDTO) {
                if (StringUtils.equals(detail.getPreTestCategorySeq(), category.getPreTestCategorySeq())) {
                    listDetail.add(detail);
                }
                for (PreTestDetailSelectDTO selectDTO : detailSelectDTO) {
                    if (StringUtils.equals(detail.getPreTestDetailSeq(), selectDTO.getPreTestDetailSeq())) {
                        detail.setCheckYn(Constants.STATE_Y);
                        detail.setContentDetail(selectDTO.getAddContent());
                    }
                }
            }
            category.setPreTestDetailDTOList(listDetail);
        }
        memberInfoDTO.setPreTestCategoryDTOList(preTestCategoryDTO);
        return memberInfoDTO;
    }

    @Override
    public CorporateMemberInfoDTO getDetailCorporatePreTest(String internPlanSeq) {
        List<PreTestDetailSelectDTO> lstDetailCategorySelected = new ArrayList<>();
        CmmCorporateMembersDTO cmmCorporateMembersDTO = commonService.getCorporateMembersInfo();

        //get corporate info by internPlanSeq and regId
        ICorporateMemberInfoDTO corporateInfo = preTestRepository.getCorporateInfoByInternPlanSeq(internPlanSeq,
                cmmCorporateMembersDTO.getCormUserId(), Constants.CD_CORM_APPROVAL, Constants.INTERN_PLAN_STATUS_ACCEPTED,
                Constants.STATE_N).orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));

        CorporateMemberInfoDTO resultDTO = modelMapper.map(corporateInfo, CorporateMemberInfoDTO.class);

        //get list pre-test category
        List<IPreTestCategoryDTO> lstCategoryEntity = preTestRepository.selectPreTestCategory(Constants.STATE_N);
        List<PreTestCategoryDTO> lstCategoryDTO = lstCategoryEntity.stream()
                .map(category -> modelMapper.map(category, PreTestCategoryDTO.class)).toList();

        //get list detail pre-test category (answer)
        List<IPreTestDetailDTO> lstDetailCategory = preTestRepository.selectPreTestDetail(Constants.STATE_N);
        List<PreTestDetailDTO> lstDetailCategoryDTO = lstDetailCategory.stream()
                .map(detail -> modelMapper.map(detail, PreTestDetailDTO.class)).toList();

        //get list selected detail pre-test category (selected answer)
        if (resultDTO.getCorporatePreTestSeq() != null) {

            List<IPreTestDetailSelectDTO> lstSelectedDetailCategory = preTestRepository.getSelectDetailCategory(
                    resultDTO.getCorporatePreTestSeq(), Constants.STATE_N);
            lstDetailCategorySelected = lstSelectedDetailCategory.stream()
                    .map(itemSelect -> modelMapper.map(itemSelect, PreTestDetailSelectDTO.class)).toList();

            resultDTO.setPreTestDetailSelectDTOList(lstDetailCategorySelected);
        }

        //add list pre-test category to detail corporate pre-test
        for (PreTestCategoryDTO category : lstCategoryDTO) {
            List<PreTestDetailDTO> listDetailPreTestDTO = new ArrayList<>();

            for (PreTestDetailDTO detail : lstDetailCategoryDTO) {
                if (StringUtils.equals(detail.getPreTestCategorySeq(), category.getPreTestCategorySeq())) {
                    listDetailPreTestDTO.add(detail);
                }

                //add selected detail pre-test category to detail pre-test category
                for (PreTestDetailSelectDTO selectDTO : lstDetailCategorySelected) {
                    if (StringUtils.equals(detail.getPreTestDetailSeq(), selectDTO.getPreTestDetailSeq())) {
                        detail.setCheckYn(Constants.STATE_Y);
                        detail.setContentDetail(selectDTO.getAddContent());
                    }
                }
            }
            category.setPreTestDetailDTOList(listDetailPreTestDTO);
        }
        resultDTO.setPreTestCategoryDTOList(lstCategoryDTO);

        return resultDTO;
    }

    @Override
    public Boolean insertCorporatePreTest(CorporateMemberInfoDTO memberInfoDTO) {
        int cntSave = 0;
        String corporatePreTestSeq = Constants.IS_BLANK;

        preTestAnsRepository.updateDelAll(memberInfoDTO.getCorporatePreTestSeq(), Constants.STATE_Y);

        if (StringUtils.equals(memberInfoDTO.getCorporatePreTestSeq(), null)) {
            CorporatePreTestEntity corporatePreTestEntity = new CorporatePreTestEntity();
            corporatePreTestEntity.setStandardIndustryType(memberInfoDTO.getStandardIndustryType());
            corporatePreTestEntity.setCorporatePreTestSeq(
                    sequenceGenerator.getNextSequence(PREFIX_CORPORATE_PRE_TEST.toString(),
                            TABLE_CORPORATE_PRE_TEST.toString()));
            corporatePreTestEntity.setInternPlanSeq(memberInfoDTO.getInternPlanSeq());
            corporatePreTestEntity.setDelYn(Constants.STATE_N);
            preTestRepository.save(corporatePreTestEntity);
            corporatePreTestSeq = corporatePreTestEntity.getCorporatePreTestSeq();
        } else {
            corporatePreTestSeq = memberInfoDTO.getCorporatePreTestSeq();
            preTestRepository.updateIndustry(corporatePreTestSeq, memberInfoDTO.getStandardIndustryType());
        }

        for (PreTestCategoryDTO category : memberInfoDTO.getPreTestCategoryDTOList()) {
            for (PreTestDetailDTO detail : category.getPreTestDetailDTOList()) {
                if (detail.getCheckYn() != null) {
                    if (StringUtils.equals(detail.getCheckYn(), Constants.STATE_Y)) {

                        PreTestAnsEntity preTestAnsEntity = new PreTestAnsEntity();

                        preTestAnsEntity.setPreTestAnsSeq(
                                sequenceGenerator.getNextSequence(PREFIX_PRE_TEST_ANS.toString(),
                                        TABLE_PRE_TEST_ANS.toString()));

                        preTestAnsEntity.setPreTestDetailSeq(detail.getPreTestDetailSeq());
                        preTestAnsEntity.setAddContent(detail.getContentDetail());
                        preTestAnsEntity.setDelYn(Constants.STATE_N);
                        preTestAnsEntity.setCorporatePreTestSeq(corporatePreTestSeq);
                        preTestAnsRepository.save(preTestAnsEntity);
                        cntSave = cntSave + 1;

                    }
                }

            }
        }

        return cntSave > 0;
    }

}
