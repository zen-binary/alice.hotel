package com.winwintek.web.backend.api.domain.client.professor.evaluationReport.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.client.professor.evaluationReport.service.EvaluationReportProfService;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.EvaluationReportProfDetailResDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.EvaluationReportProfDetailSearchDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.EvaluationReportProfReqDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.summaryReport.SummaryReportProfResDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.weekReport.PlanDetailProfResDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.weekReport.WeekCalendarProfResDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.weekReport.WeekReportProfResDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.weekReport.WeekReportProfSearchDTO;
import com.winwintek.web.backend.api.entity.client.professor.evaluationReport.EvaluationReportProfEntity;
import com.winwintek.web.backend.api.entity.client.professor.evaluationReport.IEvaluationReportDetailResponse;
import com.winwintek.web.backend.api.entity.client.professor.evaluationReport.IStudentInfoEvaluationResponse;
import com.winwintek.web.backend.api.entity.client.professor.evaluationReport.summaryReport.ISummaryReportProfResponse;
import com.winwintek.web.backend.api.entity.client.professor.evaluationReport.weekReport.IPlanDetailProfResponse;
import com.winwintek.web.backend.api.entity.client.professor.evaluationReport.weekReport.IStudentInfoResponse;
import com.winwintek.web.backend.api.entity.client.professor.evaluationReport.weekReport.IWeekReportProfResponse;
import com.winwintek.web.backend.api.repository.client.professor.evaluationReport.EvaluationReportProfRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_INTERN_PROCESS_EVALUATION;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_INTERN_PROCESS_EVALUATION;

@Service
public class EvaluationReportProfServiceImpl implements EvaluationReportProfService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private EvaluationReportProfRepository evaluationReportProfRepository;

    @Override
    public EvaluationReportProfDetailResDTO getDetailEvaluationReport(EvaluationReportProfDetailSearchDTO searchDTO) {
        //get student information evaluation by internPlanId and internStdId
        IStudentInfoEvaluationResponse studentInfo = evaluationReportProfRepository.getStudentInfoByStdId(
                searchDTO.getInternStdId(), Constants.STATE_N);

        //get detail evaluation report by internPlanId and internStdId
        IEvaluationReportDetailResponse result = evaluationReportProfRepository.getDetailEvaluationReport(
                searchDTO.getInternPlanId(), searchDTO.getInternStdId(), Constants.STATE_N);

        EvaluationReportProfDetailResDTO resultDTO = modelMapper.map(studentInfo,
                EvaluationReportProfDetailResDTO.class);

        //if result == null -> create guidance report, else update
        if (result != null) {
            modelMapper.map(result, resultDTO);
            resultDTO.setIsUpdate(Constants.STATE_Y);
        } else {
            resultDTO.setIsUpdate(Constants.STATE_N);
        }

        return resultDTO;
    }

    @Override
    @Transactional
    public String createEvaluationReport(EvaluationReportProfReqDTO dto) {
        EvaluationReportProfEntity entity = modelMapper.map(dto, EvaluationReportProfEntity.class);

        entity.setInternProcessEvaluationSeq(
                sequenceGenerator.getNextSequence(PREFIX_INTERN_PROCESS_EVALUATION.toString(),
                        TABLE_INTERN_PROCESS_EVALUATION.toString()));
        entity.setDelYn(Constants.STATE_N);

        evaluationReportProfRepository.save(entity);

        return entity.getInternProcessEvaluationSeq();
    }

    @Override
    @Transactional
    public String updateEvaluationReport(EvaluationReportProfReqDTO dto) {
        if (StringUtils.isEmpty(dto.getInternProcessEvaluationSeq())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        EvaluationReportProfEntity entity = evaluationReportProfRepository.findByInternProcessEvaluationSeqAndDelYn(
                dto.getInternProcessEvaluationSeq(), Constants.STATE_N);
        modelMapper.map(dto, entity);
        evaluationReportProfRepository.save(entity);

        return entity.getInternProcessEvaluationSeq();
    }

    @Override
    public SummaryReportProfResDTO getDetailSummaryReport(String internStdId) {
        ISummaryReportProfResponse result = evaluationReportProfRepository.getDetailSummaryReport(internStdId,
                Constants.STATE_N);

        SummaryReportProfResDTO resultDTO = modelMapper.map(result, SummaryReportProfResDTO.class);

        ClobJavaType clobJavaType = new ClobJavaType();
        String content = result.getContent() != null ? clobJavaType.toString(result.getContent()) : "";
        resultDTO.setContent(content);

        return resultDTO;
    }

    @Override
    public List<PlanDetailProfResDTO> getListWeekByInternPlan(String internPlanId) {
        List<IPlanDetailProfResponse> lstWeek = evaluationReportProfRepository.getPlanDetail(internPlanId, null,
                Constants.STATE_N);

        List<PlanDetailProfResDTO> lstWeekDTO = lstWeek.stream()
                .map(item -> modelMapper.map(item, PlanDetailProfResDTO.class)).toList();

        return lstWeekDTO;
    }

    @Override
    public WeekReportProfResDTO getDetailWeekReport(WeekReportProfSearchDTO searchDTO) {
        WeekReportProfResDTO resultDTO = new WeekReportProfResDTO();

        //get intern plan detail by internDetailId
        List<IPlanDetailProfResponse> lstPlanDetail = evaluationReportProfRepository.getPlanDetail(null,
                searchDTO.getInternDetailId(), Constants.STATE_N);

        if (! lstPlanDetail.isEmpty()) {
            PlanDetailProfResDTO planDetailEntity = modelMapper.map(lstPlanDetail.getFirst(),
                    PlanDetailProfResDTO.class);

            if (StringUtils.isEmpty(planDetailEntity.getInternStartDate()) || StringUtils.isEmpty(
                    planDetailEntity.getInternEndDate())) {
                throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
            }

            //create list week calendar with internStartDate and internEndDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.FORMAT_DATE_YYYY_MM_DD);

            LocalDate startDate = LocalDate.parse(planDetailEntity.getInternStartDate(), formatter);
            LocalDate endDate = LocalDate.parse(planDetailEntity.getInternEndDate(), formatter);

            List<WeekCalendarProfResDTO> lstWeekCalendar = startDate.datesUntil(endDate.plusDays(1)).map(item -> {
                WeekCalendarProfResDTO weekCalendarEntity = new WeekCalendarProfResDTO();
                weekCalendarEntity.setDateWeek(item.format(formatter));
                weekCalendarEntity.setIsDisabled(Constants.STATE_Y);

                return weekCalendarEntity;
            }).collect(Collectors.toCollection(ArrayList::new));

            if (StringUtils.isNotEmpty(searchDTO.getInternStdId())) {
                //get student info
                IStudentInfoResponse studentInfo = evaluationReportProfRepository.getStudentInfo(
                                searchDTO.getInternStdId(), Constants.INTERN_STD_APPROVE_STATUS,
                                Constants.INTERN_PLAN_STATUS_ACCEPTED, Constants.CD_CORM_APPROVAL, Constants.STATE_N)
                        .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));

                modelMapper.map(studentInfo, resultDTO);

                //check day in week and remove day not exist in lstWeekCalendar
                if (StringUtils.isNotEmpty(resultDTO.getDayInWeek())) {
                    lstWeekCalendar.removeIf(item -> {
                        LocalDate date = LocalDate.parse(item.getDateWeek(), formatter);
                        String dayOfWeekStr = convertDayOfWeek(date.getDayOfWeek());

                        return ! resultDTO.getDayInWeek().contains(dayOfWeekStr);
                    });
                }

                //get list week report by internStdId and internDetailId
                List<IWeekReportProfResponse> lstResult = evaluationReportProfRepository.getDetailWeekReport(
                        searchDTO.getInternDetailId(), searchDTO.getInternStdId(), Constants.STATUS_ABSENCE_ATTENDANCE,
                        Constants.STATUS_HOLIDAY_ATTENDANCE, Constants.STATE_N);

                if (! lstResult.isEmpty()) {
                    //if attendanceDate equal dateWeek => copy from item in lstResult to item in lstResultDTO
                    lstWeekCalendar.forEach(item -> {
                        lstResult.forEach(elm -> {
                            if (StringUtils.equals(elm.getAttendanceDate(), item.getDateWeek())) {
                                modelMapper.map(elm, item);
                            }
                        });
                    });
                }
            }

            resultDTO.setLstWeekCalendar(lstWeekCalendar);
        }

        return resultDTO;
    }

    private String convertDayOfWeek(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "2";
            case TUESDAY:
                return "3";
            case WEDNESDAY:
                return "4";
            case THURSDAY:
                return "5";
            case FRIDAY:
                return "6";
            case SATURDAY:
                return "7";
            case SUNDAY:
                return "1";
            default:
                return "";
        }
    }

}
