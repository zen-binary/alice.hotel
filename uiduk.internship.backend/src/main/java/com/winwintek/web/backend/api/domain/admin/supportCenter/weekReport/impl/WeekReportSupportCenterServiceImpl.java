package com.winwintek.web.backend.api.domain.admin.supportCenter.weekReport.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.supportCenter.weekReport.service.WeekReportSupportCenterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport.PlanDetailSupportCenterResDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport.WeekCalendarSupportCenterResDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport.WeekReportSupportCenterReqDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport.WeekReportSupportCenterResDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport.WeekReportSupportCenterSearchDTO;
import com.winwintek.web.backend.api.entity.admin.supportCenter.weekReport.IPlanDetailSupportCenterResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.weekReport.IStudentInfoResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.weekReport.IWeekReportSupportCenterResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.weekReport.WeekReportSupportCenterEntity;
import com.winwintek.web.backend.api.repository.admin.supportCenter.weekReport.WeekReportSupportCenterRepository;
import com.winwintek.web.backend.api.util.Utils;
import org.apache.commons.lang3.StringUtils;
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

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_STD_WEEK_REPORT;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_STD_WEEK_REPORT;

@Service
public class WeekReportSupportCenterServiceImpl implements WeekReportSupportCenterService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private WeekReportSupportCenterRepository weekReportSupportCenterRepository;

    @Override
    public List<PlanDetailSupportCenterResDTO> getListWeekByInternPlan(String internPlanId) {
        List<IPlanDetailSupportCenterResponse> lstWeek = weekReportSupportCenterRepository.getPlanDetail(internPlanId,
                null, Constants.STATE_N);

        List<PlanDetailSupportCenterResDTO> lstWeekDTO = lstWeek.stream()
                .map(item -> modelMapper.map(item, PlanDetailSupportCenterResDTO.class)).toList();

        return lstWeekDTO;
    }

    @Override
    public WeekReportSupportCenterResDTO getDetailWeekReport(WeekReportSupportCenterSearchDTO searchDTO) {
        WeekReportSupportCenterResDTO resultDTO = new WeekReportSupportCenterResDTO();

        //get intern plan detail by internDetailId
        List<IPlanDetailSupportCenterResponse> lstPlanDetail = weekReportSupportCenterRepository.getPlanDetail(null,
                searchDTO.getInternDetailId(), Constants.STATE_N);

        if (! lstPlanDetail.isEmpty()) {
            PlanDetailSupportCenterResDTO planDetailEntity = modelMapper.map(lstPlanDetail.getFirst(),
                    PlanDetailSupportCenterResDTO.class);

            if (StringUtils.isEmpty(planDetailEntity.getInternStartDate()) || StringUtils.isEmpty(
                    planDetailEntity.getInternEndDate())) {
                throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
            }

            //create list week calendar with internStartDate and internEndDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.FORMAT_DATE_YYYY_MM_DD);

            LocalDate startDate = LocalDate.parse(planDetailEntity.getInternStartDate(), formatter);
            LocalDate endDate = LocalDate.parse(planDetailEntity.getInternEndDate(), formatter);

            List<WeekCalendarSupportCenterResDTO> lstWeekCalendar = startDate.datesUntil(endDate.plusDays(1))
                    .map(item -> {
                        WeekCalendarSupportCenterResDTO weekCalendarEntity = new WeekCalendarSupportCenterResDTO();
                        weekCalendarEntity.setDateWeek(item.format(formatter));
                        weekCalendarEntity.setIsDisabled(Constants.STATE_Y);

                        return weekCalendarEntity;
                    }).collect(Collectors.toCollection(ArrayList::new));

            if (StringUtils.isNotEmpty(searchDTO.getInternStdId())) {
                //get student info
                IStudentInfoResponse studentInfo = weekReportSupportCenterRepository.getStudentInfo(
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
                List<IWeekReportSupportCenterResponse> lstResult = weekReportSupportCenterRepository.getDetailWeekReport(
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

    @Override
    @Transactional
    public List<String> createWeekReport(List<WeekReportSupportCenterReqDTO> lstDTO) {
        List<String> lstResultDTO = new ArrayList<>();
        if (! lstDTO.isEmpty()) {
            List<WeekReportSupportCenterEntity> lstEntity = new ArrayList<>();

            for (WeekReportSupportCenterReqDTO item : lstDTO) {

                WeekReportSupportCenterEntity entity = new WeekReportSupportCenterEntity();

                if (StringUtils.isNotEmpty(item.getStdWeekReportSeq())) {
                    //find week report by stdWeekReportSeq
                    entity = weekReportSupportCenterRepository.findByStdWeekReportSeqAndDelYn(
                            item.getStdWeekReportSeq(), Constants.STATE_N);
                    if (entity != null) {
                        //update week report
                        modelMapper.map(item, entity);
                    } else {
                        throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
                    }
                } else {
                    //create week report
                    entity = modelMapper.map(item, WeekReportSupportCenterEntity.class);
                    entity.setStdWeekReportSeq(sequenceGenerator.getNextSequence(PREFIX_STD_WEEK_REPORT.toString(),
                            TABLE_STD_WEEK_REPORT.toString()));
                    entity.setDelYn(Constants.STATE_N);
                }

                if (StringUtils.isNotEmpty(item.getDateWeek())) {
                    entity.setDateWeek(Utils.formatDate(item.getDateWeek()));
                }

                lstEntity.add(entity);
            }

            weekReportSupportCenterRepository.saveAll(lstEntity);

            lstResultDTO = lstEntity.stream().map(WeekReportSupportCenterEntity::getStdWeekReportSeq).toList();
        }

        return lstResultDTO;
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
