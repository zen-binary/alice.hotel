package com.winwintek.web.backend.api.domain.admin.internPlanCenter.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.domain.admin.internPlanCenter.service.InternPlanCenterService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.admin.cmmCorporateMember.CmmCorporateMembersDTO;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterDetailDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterDetailListDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterListDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterSearchDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterStsReqDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterStudentDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterStudentListDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCountStsDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCountStsReqDto;
import com.winwintek.web.backend.api.dto.admin.internPlanDetail.InternPlanDetailListDto;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanCenterDetailResponse;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanCenterMng;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanCenterResponse;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanCenterStudentResponse;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanCountStsResponse;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanDetailListCenterResponse;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanStdCenterMng;
import com.winwintek.web.backend.api.repository.admin.internPlanCenter.InternPlanCenterRepository;
import com.winwintek.web.backend.api.repository.admin.internPlanCenter.InternPlanDetailCenterRepository;
import com.winwintek.web.backend.api.repository.admin.internPlanCenter.InternPlanStdCenterRepository;
import com.winwintek.web.backend.api.util.Utils;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InternPlanCenterServiceImpl implements InternPlanCenterService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InternPlanCenterRepository internPlanCenterRepo;

    @Autowired
    private InternPlanStdCenterRepository internPlanStdCenterRepo;

    @Autowired
    private InternPlanDetailCenterRepository  internPlanDetailCenterRepo;


    @Override
    public Page<InternPlanCenterListDto> getListInternPlanCenter(InternPlanCenterSearchDto dto) {
        Pageable pageable = commonService.buildPageable(dto.getPage(), dto.getSize(), dto.getSort());
        Page<InternPlanCenterResponse> getPageInternPlan = internPlanCenterRepo.getListInternPlanCenter(pageable, dto,
                Constants.STATE_N);
        Page<InternPlanCenterListDto> listDto = getPageInternPlan.map(item -> {
            InternPlanCenterListDto planListDto = modelMapper.map(item, InternPlanCenterListDto.class);
            if (Objects.nonNull(item.getRegisterStr()) && Objects.nonNull(item.getRegisterEnd())){
                planListDto.setRegisterDate(
                        Utils.formatDateToString(item.getRegisterStr()) + " ~ " + Utils.formatDateToString(
                                item.getRegisterEnd()));
            }
            if (Objects.nonNull(item.getInternShipStr()) && Objects.nonNull(item.getInternShipEnd())){
                planListDto.setInternShipDate(
                        Utils.formatDateToString(item.getInternShipStr()) + " ~ " + Utils.formatDateToString(
                                item.getInternShipEnd()));
            }

            return planListDto;
        });
        return listDto;
    }

    @Override
    public Page<InternPlanCenterStudentListDto> getListInternPlanStudent(InternPlanCenterStudentDto dto) {
        Pageable pageable = commonService.buildPageable(dto.getPage(), dto.getSize(), dto.getSort());
        Page<InternPlanCenterStudentResponse> getPageInternPlanStuden = internPlanStdCenterRepo.getListInternPlanStudent(
                pageable, dto.getIdInternPlan(), Constants.STATE_N);

        Page<InternPlanCenterStudentListDto> listDto = getPageInternPlanStuden.map(
                item -> modelMapper.map(item, InternPlanCenterStudentListDto.class));
        return listDto;
    }

    @Override
    public InternPlanCountStsDto getStatusStudentInternPlan(InternPlanCountStsReqDto dto) {
        dto.setWaitApproveStatusCount(Constants.CD_STD_WAIT_APPROVAL);
        dto.setApproveStatusCount(Constants.CD_STD_APPROVAL);
        dto.setNotApproveStatusCount(Constants.CD_STD_NOT_APPROVAL);
        InternPlanCountStsResponse internPlanCountSts = internPlanStdCenterRepo.getStatusStudentInternPlan(dto,
                Constants.STATE_N);
        InternPlanCountStsDto result = modelMapper.map(internPlanCountSts, InternPlanCountStsDto.class);
        return result;
    }

    @Override
    @Transactional
    public boolean deleteInternPlan(List<String> lstIdInternPlan) {
        List<InternPlanCenterMng> lstInternPlan = new ArrayList<>();
        lstIdInternPlan.forEach(item ->{
            InternPlanCenterMng entity = internPlanCenterRepo.findById(item).orElseThrow(() -> new CustomException(
                    ApiStatus.BAD_REQUEST_VALID));
            entity.setDelYn(Constants.STATE_Y);
            lstInternPlan.add(entity);
        });
        internPlanCenterRepo.saveAll(lstInternPlan);
        return true;
    }

    @Override
    @Transactional
    public boolean approveInternPlan(InternPlanCenterStsReqDto req) {
        req.setStsInternPlan(Constants.CD_INTERN_PLAN_APPROVAL);
        return changeStsInternPlan(req);
    }

    @Override
    @Transactional
    public boolean rejectApproveInternPlan(InternPlanCenterStsReqDto req) {
        req.setStsInternPlan(Constants.CD_INTERN_PLAN_REJECT_APPROVAL);
        return changeStsInternPlan(req);
    }

    @Override
    @Transactional
    public boolean cancelApproveInternPlan(InternPlanCenterStsReqDto req) {
        req.setStsInternPlan(Constants.CD_INTERN_PLAN_WAIT_APPROVAL);
        return changeStsInternPlan(req);
    }

    @Override
    @Transactional
    public boolean deleteInternStd(List<String> lstIdInternStd) {
        List<InternPlanStdCenterMng> lstInternStd = new ArrayList<>();
        lstIdInternStd.forEach(item ->{
            InternPlanStdCenterMng entity = internPlanStdCenterRepo.findById(item).orElseThrow(() -> new CustomException(
                    ApiStatus.BAD_REQUEST_VALID));
            entity.setDelYn(Constants.STATE_Y);
            lstInternStd.add(entity);
        });
        internPlanStdCenterRepo.saveAll(lstInternStd);
        return true;
    }

    @Override
    @Transactional
    public boolean approveInternStd(List<String> lstIdInternStd) {
        return changeStsInternStd(lstIdInternStd, Constants.CD_STD_APPROVAL);
    }

    @Override
    @Transactional
    public boolean notApproveInternStd(List<String> lstIdInternStd) {
        return changeStsInternStd(lstIdInternStd, Constants.CD_STD_NOT_APPROVAL);
    }

    @Override
    @Transactional
    public boolean cancelApproveInternStd(List<String> lstIdInternStd) {
        return changeStsInternStd(lstIdInternStd, Constants.CD_STD_WAIT_APPROVAL);
    }

    @Override
    public InternPlanCenterDetailDto detailInternPlan(String data) {
        InternPlanCenterDetailResponse res = internPlanCenterRepo.getInternPlanById(Constants.STATE_N, data);
        InternPlanCenterDetailDto detailDto = modelMapper.map(res, InternPlanCenterDetailDto.class);
        detailDto.setRegisterStr(Utils.formatDateToString(res.getRegisterStr()));
        detailDto.setRegisterEnd(Utils.formatDateToString(res.getRegisterEnd()));
        detailDto.setInternshipStr(Utils.formatDateToString(res.getInternshipStr()));
        detailDto.setInternshipEnd(Utils.formatDateToString(res.getInternshipEnd()));

        ClobJavaType clobJavaType = new ClobJavaType();
        detailDto.setInternJobSummary(
                res.getInternJobSummary() != null ? clobJavaType.toString(res.getInternJobSummary()) : null);
        detailDto.setRequirements(res.getRequirements() != null ? clobJavaType.toString(res.getRequirements()) : null);
        List<InternPlanDetailListCenterResponse> listDetail = internPlanDetailCenterRepo.getListDetail(data, Constants.STATE_N);
        List<InternPlanCenterDetailListDto> listDetailDto = listDetail.stream().map(item -> {
            InternPlanCenterDetailListDto internPlanDetail = modelMapper.map(item, InternPlanCenterDetailListDto.class);
            internPlanDetail.setContent(item.getContent() != null ? clobJavaType.toString(item.getContent()) : null);
            internPlanDetail.setDelYn(item.getDelYn());
            internPlanDetail.setStartDate(Utils.formatDateToString(item.getStartDate()));
            internPlanDetail.setEndDate(Utils.formatDateToString(item.getEndDate()));
            return internPlanDetail;
        }).collect(Collectors.toList());
        detailDto.setListInternPlanDetail(listDetailDto);
        return detailDto;
    }

    private boolean changeStsInternPlan(InternPlanCenterStsReqDto req) {
        List<InternPlanCenterMng> lstInternPlan = new ArrayList<>();
        req.getLstIdInternPlan().forEach(item ->{
            InternPlanCenterMng entity = internPlanCenterRepo.findById(item).orElseThrow(() -> new CustomException(
                    ApiStatus.BAD_REQUEST_VALID));

            entity.setStatus(req.getStsInternPlan());
            if (req.getStsInternPlan().equals(Constants.CD_INTERN_PLAN_WAIT_APPROVAL)){
                entity.setRejectionReasonDetails(null);
                entity.setConfirmDate(null);
            }else {
                entity.setRejectionReasonDetails(req.getReasonMessage());
                entity.setConfirmDate(new Date());
            }

            lstInternPlan.add(entity);
        });
        internPlanCenterRepo.saveAll(lstInternPlan);
        return true;
    }

    private boolean changeStsInternStd(List<String> lstIdInternStd, String status){
        List<InternPlanStdCenterMng> lstInternStd = new ArrayList<>();
        lstIdInternStd.forEach(item-> {
            InternPlanStdCenterMng entity = internPlanStdCenterRepo.findById(item).orElseThrow(() -> new CustomException(
                    ApiStatus.BAD_REQUEST_VALID));
            if (status.equals(Constants.CD_STD_APPROVAL)){
                entity.setApproveDate(new Date());
            }else {
                entity.setApproveDate(null);
            }
            if (!entity.getApproveStatus().equals(Constants.CD_STD_CANCEL_JOIN)){
                entity.setApproveStatus(status);
            }
            lstInternStd.add(entity);
        });
        internPlanStdCenterRepo.saveAll(lstInternStd);
        return true;
    }


}
