package com.winwintek.web.backend.api.domain.admin.internPlan.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.internPlan.service.InternPlanService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.admin.cmmCorporateMember.CmmCorporateMembersDTO;
import com.winwintek.web.backend.api.dto.admin.corporateManager.CorporateManagerListDto;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanCormMemberInfoDTO;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanDetailDto;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanExistingDto;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanListDto;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanRequestDto;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanSearchDto;
import com.winwintek.web.backend.api.dto.admin.internPlanDetail.InternPlanDetailListDto;
import com.winwintek.web.backend.api.dto.admin.staffIntern.StaffInternDto;
import com.winwintek.web.backend.api.dto.system.UserDTO;
import com.winwintek.web.backend.api.entity.admin.corporateManager.CorporateManagerResponse;
import com.winwintek.web.backend.api.entity.admin.internPlan.InternPlanDetailResponse;
import com.winwintek.web.backend.api.entity.admin.internPlan.InternPlanEntity;
import com.winwintek.web.backend.api.entity.admin.internPlan.InternPlanExistingResponse;
import com.winwintek.web.backend.api.entity.admin.internPlan.InternPlanResponse;
import com.winwintek.web.backend.api.entity.admin.internPlan.InternPlanTreeResponse;
import com.winwintek.web.backend.api.entity.admin.internPlanDetail.InternPlanDetailEntity;
import com.winwintek.web.backend.api.entity.admin.internPlanDetail.InternPlanListResponse;
import com.winwintek.web.backend.api.entity.admin.staffIntern.StaffInternResponse;
import com.winwintek.web.backend.api.repository.admin.corporateManager.CorporateManagerRepository;
import com.winwintek.web.backend.api.repository.admin.internPlan.InternPlanRepository;
import com.winwintek.web.backend.api.repository.admin.internPlanDetail.InternPlanDetailRepository;
import com.winwintek.web.backend.api.repository.admin.staffIntern.StaffInternRepository;
import com.winwintek.web.backend.api.util.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_INTERN_PLAN;
import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_INTERN_PLAN_DETAIL;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_INTERN_PLAN;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_INTERN_PLAN_DETAIL;

@Service
public class InternPlanServiceImpl implements InternPlanService {

    @Autowired
    private InternPlanRepository internPlanRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired

    private CommonService commonService;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private InternPlanDetailRepository internPlanDetailRepository;

    @Autowired
    private CorporateManagerRepository corporateManagerRepository;

    @Autowired
    private StaffInternRepository staffInternRepository;

    @Override
    public Page<InternPlanListDto> getListInternPlan(InternPlanSearchDto dto) {
        Pageable pageable = commonService.buildPageable(dto.getPage(), dto.getSize(), dto.getSort());
        Page<InternPlanResponse> getPageInternPlan = internPlanRepository.getListInternPlan(pageable, dto,
                Constants.STATE_N, commonService.getFullUserInfo().getUserId());
        Page<InternPlanListDto> listDto = getPageInternPlan.map(item -> {
            InternPlanListDto planListDto = modelMapper.map(item, InternPlanListDto.class);
            planListDto.setRegisterDate(
                    Utils.formatDateToString(item.getRegisterStr()) + " ~ " + Utils.formatDateToString(
                            item.getRegisterEnd()));
            planListDto.setInternShipDate(
                    Utils.formatDateToString(item.getInternShipStr()) + " ~ " + Utils.formatDateToString(
                            item.getInternShipEnd()));
            return planListDto;
        });
        return listDto;
    }

    @Override
    @Transactional
    public String create(InternPlanRequestDto dto) {
        InternPlanEntity internPlanEntity = new InternPlanEntity();

        if (StringUtils.isNotBlank(dto.getInternPlanSeq())) {
            Optional<InternPlanEntity> existingPlanOpt = internPlanRepository.findById(dto.getInternPlanSeq());
            if (existingPlanOpt.isPresent()) {
                internPlanEntity = existingPlanOpt.get();
            } else {
                throw new CustomException(ApiStatus.NOT_FOUND);
            }
        } else {
            internPlanEntity = new InternPlanEntity();
            internPlanEntity.setInternPlanSeq(
                    sequenceGenerator.getNextSequence(PREFIX_INTERN_PLAN.toString(), TABLE_INTERN_PLAN.toString()));
            internPlanEntity.setDelYn(Constants.STATE_N);
            internPlanEntity.setStatus(Constants.INTERN_PLAN_01);
        }

        BeanUtils.copyProperties(dto, internPlanEntity, "internPlanSeq");
        internPlanEntity.setRegisterStr(Utils.formatDate(dto.getRegisterStr()));
        internPlanEntity.setRegisterEnd(Utils.formatDate(dto.getRegisterEnd()));
        internPlanEntity.setInternshipStr(Utils.formatDate(dto.getInternshipStr()));
        internPlanEntity.setInternshipEnd(Utils.formatDate(dto.getInternshipEnd()));

        InternPlanEntity internPlanEntitySave = internPlanRepository.save(internPlanEntity);

        List<InternPlanDetailEntity> internPlanDetails = dto.getListInternPlanDetail().stream().map(detailDto -> {
            InternPlanDetailEntity detailEntity = modelMapper.map(detailDto, InternPlanDetailEntity.class);
            if (StringUtils.isBlank(detailEntity.getInternDetailSeq()) || ! internPlanDetailRepository.findById(
                    detailEntity.getInternDetailSeq()).isPresent()) {
                detailEntity.setInternDetailSeq(sequenceGenerator.getNextSequence(PREFIX_INTERN_PLAN_DETAIL.toString(),
                        TABLE_INTERN_PLAN_DETAIL.toString()));
                detailEntity.setInternPlanSeq(internPlanEntitySave.getInternPlanSeq());
            } else {
                detailEntity.setInternPlanSeq(internPlanEntitySave.getInternPlanSeq());
            }
            return detailEntity;
        }).collect(Collectors.toList());

        if (internPlanDetails.isEmpty()) return null;

        internPlanDetailRepository.saveAll(internPlanDetails);

        return internPlanEntity.getInternPlanSeq();
    }

    @Override
    public InternPlanDetailDto detail(String data) {
        InternPlanDetailResponse res = internPlanRepository.getInternPlanById(Constants.STATE_N, data);
        InternPlanDetailDto detailDto = modelMapper.map(res, InternPlanDetailDto.class);
        CmmCorporateMembersDTO cmmCorporateMembersDTO = commonService.getCorporateMembersInfo();
        detailDto.setRegisterStr(Utils.formatDateToString(res.getRegisterStr()));
        detailDto.setRegisterEnd(Utils.formatDateToString(res.getRegisterEnd()));
        detailDto.setInternshipStr(Utils.formatDateToString(res.getInternshipStr()));
        detailDto.setInternshipEnd(Utils.formatDateToString(res.getInternshipEnd()));
        detailDto.setCormSeq(cmmCorporateMembersDTO.getCormSeq());

        ClobJavaType clobJavaType = new ClobJavaType();
        detailDto.setInternJobSummary(
                res.getInternJobSummary() != null ? clobJavaType.toString(res.getInternJobSummary()) : null);
        detailDto.setRequirements(res.getRequirements() != null ? clobJavaType.toString(res.getRequirements()) : null);
        List<InternPlanListResponse> listDetail = internPlanDetailRepository.getListDetail(data, Constants.STATE_N);
        List<InternPlanDetailListDto> listDetailDto = listDetail.stream().map(item -> {
            InternPlanDetailListDto internPlanDetail = modelMapper.map(item, InternPlanDetailListDto.class);
            internPlanDetail.setContent(item.getContent() != null ? clobJavaType.toString(item.getContent()) : null);
            internPlanDetail.setDelYn(item.getDelYn());
            internPlanDetail.setStartDate(Utils.formatDateToString(item.getStartDate()));
            internPlanDetail.setEndDate(Utils.formatDateToString(item.getEndDate()));
            return internPlanDetail;
        }).collect(Collectors.toList());
        detailDto.setListInternPlanDetail(listDetailDto);
        return detailDto;
    }

    @Override
    public List<InternPlanExistingDto> getInternPlanExisting() {
        UserDTO userDTO = commonService.getFullUserInfo();
        List<InternPlanExistingResponse> listInternPlan = internPlanRepository.getInternPlanExisting(
                userDTO.getUserId(), Constants.STATE_N);
        List<InternPlanExistingDto> listExistingDto = listInternPlan.stream()
                .map(item -> modelMapper.map(item, InternPlanExistingDto.class)).collect(Collectors.toList());
        return listExistingDto;
    }

    @Override
    public List<InternPlanExistingDto> getInternPlanExistingUpdate(String internPlanSeq) {
        UserDTO userDTO = commonService.getFullUserInfo();
        List<InternPlanExistingResponse> listInternPlan = internPlanRepository.getInternPlanExistingUpdate(
                userDTO.getUserId(), internPlanSeq, Constants.STATE_N);
        List<InternPlanExistingDto> listExistingDto = listInternPlan.stream()
                .map(item -> modelMapper.map(item, InternPlanExistingDto.class)).collect(Collectors.toList());
        return listExistingDto;
    }

    @Override
    public Integer executionCount(String data) {
        List<InternPlanTreeResponse> listInternPlanTree = internPlanRepository.getInternPlanTree(Constants.STATE_N);
        String parentSeq = null;
        for (InternPlanTreeResponse listTree : listInternPlanTree) {
            if (data != null && data.equals(listTree.getInternPlanSeq())) {
                parentSeq = listTree.getTreeRoot();
            }
        }

        Integer executionMax = internPlanRepository.countInternPlanTreeById(parentSeq, Constants.STATE_N);
        if (Objects.isNull(executionMax)) {
            executionMax = 1;
        }
        return executionMax + 1;
    }

    @Override
    public List<CorporateManagerListDto> getCorporateManager() {
        CmmCorporateMembersDTO cmmCorporateMembersDTO = commonService.getCorporateMembersInfo();
        List<CorporateManagerResponse> res = corporateManagerRepository.getCorporateManager(
                cmmCorporateMembersDTO.getCormSeq(), Constants.STATE_N);
        List<CorporateManagerListDto> corporateManagerList = res.stream()
                .map(item -> modelMapper.map(item, CorporateManagerListDto.class)).collect(Collectors.toList());
        return corporateManagerList;
    }

    @Override
    public List<StaffInternDto> getListStaff() {
        List<StaffInternResponse> res = staffInternRepository.getListStaff();
        List<StaffInternDto> staffInternList = res.stream().map(item -> modelMapper.map(item, StaffInternDto.class))
                .collect(Collectors.toList());
        return staffInternList;
    }

    @Override
    @Transactional
    public Boolean delete(List<String> data) {
        List<InternPlanEntity> listInternPlan = new ArrayList<>();
        if (data.size() > 0) {
            for (String seq : data) {
                Optional<InternPlanEntity> internPlanEntity = internPlanRepository.findById(seq);
                if (! internPlanEntity.isPresent()) {
                    return false;
                }
                if (! internPlanEntity.get().getStatus().equals(Constants.INTERN_PLAN_01)) {
                    throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
                }
                internPlanEntity.get().setDelYn(Constants.STATE_Y);
                listInternPlan.addLast(internPlanEntity.get());
            }
            internPlanRepository.saveAll(listInternPlan);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public InternPlanCormMemberInfoDTO getInternPlanCormMemberInfo() {
        CmmCorporateMembersDTO cmmCorporateMembersDTO = commonService.getCorporateMembersInfo();
        InternPlanCormMemberInfoDTO internPlanCormMemberInfoDTO = modelMapper.map(cmmCorporateMembersDTO,
                InternPlanCormMemberInfoDTO.class);

        return internPlanCormMemberInfoDTO;
    }

}
