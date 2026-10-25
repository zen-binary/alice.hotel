package com.winwintek.web.backend.api.domain.client.student.reportWeek.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.client.student.reportWeek.service.ReportWeekService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.client.student.reportWeek.ListWeekClient;
import com.winwintek.web.backend.api.dto.client.student.reportWeek.SearchWeekReportDTO;
import com.winwintek.web.backend.api.dto.client.student.reportWeek.WeekClientDTO;
import com.winwintek.web.backend.api.dto.system.UserDTO;
import com.winwintek.web.backend.api.entity.admin.internPlan.InternPlanEntity;
import com.winwintek.web.backend.api.entity.admin.internPlanDetail.InternPlanDetailEntity;
import com.winwintek.web.backend.api.entity.client.student.reportWeek.IListWeekResponse;
import com.winwintek.web.backend.api.entity.client.student.reportWeek.IWeekReportStudentResponse;
import com.winwintek.web.backend.api.entity.client.student.reportWeek.ReportWeekStudentEntity;
import com.winwintek.web.backend.api.entity.client.student.reportWeek.WeekCalendarClientResDTO;
import com.winwintek.web.backend.api.entity.client.student.reportWeek.WeekReportClientSearchDTO;
import com.winwintek.web.backend.api.entity.client.student.reportWeek.WeekReportInternStdEntity;
import com.winwintek.web.backend.api.entity.client.student.reportWeek.WeekReportStdAttendanceEntity;
import com.winwintek.web.backend.api.repository.admin.internPlan.InternPlanRepository;
import com.winwintek.web.backend.api.repository.admin.internPlanDetail.InternPlanDetailRepository;
import com.winwintek.web.backend.api.repository.client.student.reportWeek.ReportWeekInternStdRepository;
import com.winwintek.web.backend.api.repository.client.student.reportWeek.ReportWeekRepository;
import com.winwintek.web.backend.api.repository.client.student.reportWeek.WeekReportStdAttendanceRepository;
import com.winwintek.web.backend.api.util.Utils;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_STD_WEEK_REPORT;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_STD_WEEK_REPORT;

@Service
public class ReportWeekServiceImpl implements ReportWeekService {

    @Autowired
    private ReportWeekRepository reportWeekRepository;

    @Autowired
    private InternPlanDetailRepository internPlanDetailRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private InternPlanRepository internPlanRepository;

    @Autowired
    private ReportWeekInternStdRepository reportWeekInternStdRepository;

    @Autowired
    private WeekReportStdAttendanceRepository weekReportStdAttendanceRepository;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Override
    public WeekClientDTO getListWeekClient(SearchWeekReportDTO dto) {
        UserDTO userDTO = commonService.getFullUserInfo();

        WeekClientDTO detailDTO = new WeekClientDTO();

        IWeekReportStudentResponse studentInfo = reportWeekRepository.getInfoStudentClient(userDTO.getUserId(),
                dto.getInternPlanId(), Constants.CD_STD_APPROVAL, Constants.STATE_N);

        if (studentInfo != null) {
            detailDTO = modelMapper.map(studentInfo, WeekClientDTO.class);
        }

        List<IListWeekResponse> lstResult = reportWeekRepository.getListWeekClient(dto.getInternPlanId(),
                Constants.STATE_N);

        if (lstResult != null) {
            List<ListWeekClient> lstWeek = lstResult.stream()
                    .map(entity -> modelMapper.map(entity, ListWeekClient.class)).collect(Collectors.toList());
            detailDTO.setLstWeek(lstWeek);
        }

        return detailDTO;
    }

    @Override
    public List<WeekCalendarClientResDTO> getListDetailWeekReport(WeekReportClientSearchDTO searchDTO) {
        UserDTO userDTO = commonService.getFullUserInfo();
        List<WeekCalendarClientResDTO> listWeekCalenderDTO = new ArrayList<>();

        Optional<InternPlanDetailEntity> internPlanDetailEntity = internPlanDetailRepository.findById(
                searchDTO.getInternDetailId());
        if (! internPlanDetailEntity.isPresent()) {
            throw new CustomException(ApiStatus.NOT_FOUND);
        }

        Optional<InternPlanEntity> internPlanEntity = internPlanRepository.findById(
                internPlanDetailEntity.get().getInternPlanSeq());
        if (! internPlanEntity.isPresent()) {
            throw new CustomException(ApiStatus.NOT_FOUND);
        }

        Optional<WeekReportInternStdEntity> internStd = reportWeekInternStdRepository.findByInternPlanSeqAndStdNoAndDelYnAndApproveStatus(
                internPlanEntity.get().getInternPlanSeq(), userDTO.getUserId(), Constants.STATE_N,
                Constants.CD_STD_APPROVAL);

        if (! internStd.isPresent()) {
            throw new CustomException(ApiStatus.NOT_FOUND);
        }

        List<ReportWeekStudentEntity> reportWeekStudent = reportWeekRepository.findAllByInternDetailSeqAndInternStdSeqAndDelYn(
                searchDTO.getInternDetailId(), internStd.get().getInternStdSeq(), Constants.STATE_N);

        List<WeekReportStdAttendanceEntity> listStdAttend = weekReportStdAttendanceRepository.findAllByInternStdSeqAndInternDetailSeqAndDelYn(
                internStd.get().getInternStdSeq(), searchDTO.getInternDetailId(), Constants.STATE_N);

        String dayInWeek = internPlanEntity.get().getInternshipDay();

        Date startDate = internPlanDetailEntity.get().getStartDate();
        Date endDate = internPlanDetailEntity.get().getEndDate();

        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (LocalDate date = start; ! date.isAfter(end); date = date.plusDays(1)) {
            String dayOfWeekStr = convertDayOfWeek(date.getDayOfWeek());

            if (dayInWeek.contains(dayOfWeekStr)) {
                WeekCalendarClientResDTO weekCalendarDTO = new WeekCalendarClientResDTO();
                String dateWeek = date.format(formatter);
                weekCalendarDTO.setDateWeek(dateWeek);
                ReportWeekStudentEntity reportEntity = findReportWeekStudentEntity(reportWeekStudent, dateWeek);
                if (Objects.nonNull(reportEntity)) {
                    weekCalendarDTO.setDateWeek(dateWeek);
                    weekCalendarDTO.setInHour(reportEntity.getInHour());
                    weekCalendarDTO.setInMinute(reportEntity.getInMinute());
                    weekCalendarDTO.setOutHour(reportEntity.getOutHour());
                    weekCalendarDTO.setOutMinute(reportEntity.getOutMinute());
                    weekCalendarDTO.setStdWeekReportId(reportEntity.getStdWeekReportSeq());
                    weekCalendarDTO.setJobContent(reportEntity.getJobContent());
                    weekCalendarDTO.setAcquiredKnowledge(reportEntity.getAcquiredKnowledge());
                }
                weekCalendarDTO.setInternPlanDetailSeq(searchDTO.getInternDetailId());
                weekCalendarDTO.setInternStdSeq(internStd.get().getInternStdSeq());

                Optional<WeekReportStdAttendanceEntity> attendanceEntityOpt = listStdAttend.stream()
                        .filter(attendance -> Utils.formatDateToString(attendance.getAttendanceDate()).equals(dateWeek))
                        .findFirst();
                if (attendanceEntityOpt.isPresent()) {
                    WeekReportStdAttendanceEntity attendanceEntity = attendanceEntityOpt.get();
                    if (Constants.STATUS_ABSENCE_ATTENDANCE.equals(
                            attendanceEntity.getStatus()) || Constants.STATUS_HOLIDAY_ATTENDANCE.equals(
                            attendanceEntity.getStatus())) {
                        weekCalendarDTO.setIsDisabled(Constants.STATE_Y);
                    } else {
                        weekCalendarDTO.setIsDisabled(Constants.STATE_N);
                    }
                } else {
                    weekCalendarDTO.setIsDisabled(Constants.STATE_Y);
                }

                listWeekCalenderDTO.add(weekCalendarDTO);
            }
        }
        return listWeekCalenderDTO;
    }

    @Override
    @Transactional
    public List<String> saveWeekReport(List<WeekCalendarClientResDTO> listWeekReportDTO) {
        List<ReportWeekStudentEntity> listReportWeekStudentEntity = new ArrayList<>();

        if (listWeekReportDTO.isEmpty()) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        List<WeekReportStdAttendanceEntity> listStdAttend = weekReportStdAttendanceRepository.findAllByInternStdSeqAndInternDetailSeqAndDelYn(
                listWeekReportDTO.get(0).getInternStdSeq(), listWeekReportDTO.get(0).getInternPlanDetailSeq(),
                Constants.STATE_N);

        for (WeekCalendarClientResDTO weekCalendarClientResDTO : listWeekReportDTO) {
            if (weekCalendarClientResDTO.getIsDisabled().equals(Constants.STATE_N)) {

                Optional<WeekReportStdAttendanceEntity> attendanceEntityOpt = listStdAttend.stream()
                        .filter(attendance -> Utils.formatDateToString(attendance.getAttendanceDate())
                                .equals(weekCalendarClientResDTO.getDateWeek())).findFirst();
                if (attendanceEntityOpt.isPresent()) {
                    WeekReportStdAttendanceEntity attendanceEntity = attendanceEntityOpt.get();
                    if (Constants.STATUS_ABSENCE_ATTENDANCE.equals(
                            attendanceEntity.getStatus()) || Constants.STATUS_HOLIDAY_ATTENDANCE.equals(
                            attendanceEntity.getStatus())) {
                        continue;
                    }
                } else {
                    continue;
                }

                ReportWeekStudentEntity reportWeekStudentEntity = new ReportWeekStudentEntity();

                if (StringUtils.isBlank(weekCalendarClientResDTO.getStdWeekReportId())) {

                    reportWeekStudentEntity.setStdWeekReportSeq(
                            sequenceGenerator.getNextSequence(PREFIX_STD_WEEK_REPORT.toString(),
                                    TABLE_STD_WEEK_REPORT.toString()));

                } else {

                    Optional<ReportWeekStudentEntity> reportWeekStudentFind = reportWeekRepository.findById(
                            weekCalendarClientResDTO.getStdWeekReportId());

                    if (! reportWeekStudentFind.isPresent()) {
                        throw new CustomException(ApiStatus.NOT_FOUND);
                    }

                    reportWeekStudentEntity = reportWeekStudentFind.get();

                }

                reportWeekStudentEntity.setDateWeek(Utils.formatDate(weekCalendarClientResDTO.getDateWeek()));
                reportWeekStudentEntity.setInHour(weekCalendarClientResDTO.getInHour());
                reportWeekStudentEntity.setInMinute(weekCalendarClientResDTO.getInMinute());
                reportWeekStudentEntity.setOutHour(weekCalendarClientResDTO.getOutHour());
                reportWeekStudentEntity.setOutMinute(weekCalendarClientResDTO.getOutMinute());
                reportWeekStudentEntity.setDelYn(Constants.STATE_N);
                reportWeekStudentEntity.setJobContent(weekCalendarClientResDTO.getJobContent());
                reportWeekStudentEntity.setAcquiredKnowledge(weekCalendarClientResDTO.getAcquiredKnowledge());
                reportWeekStudentEntity.setInternDetailSeq(weekCalendarClientResDTO.getInternPlanDetailSeq());
                reportWeekStudentEntity.setInternStdSeq(weekCalendarClientResDTO.getInternStdSeq());
                listReportWeekStudentEntity.add(reportWeekStudentEntity);
            }
        }

        List<ReportWeekStudentEntity> listReportWeekStudentEntitySave = reportWeekRepository.saveAll(
                listReportWeekStudentEntity);

        List<String> stdWeekReportSeqList = listReportWeekStudentEntitySave.stream()
                .map(ReportWeekStudentEntity::getStdWeekReportSeq).collect(Collectors.toList());

        return stdWeekReportSeqList;
    }

//    @Override
//    public String createWeekReport(WeekReportCreateDTO dto) {
//        UserDTO userDTO = commonService.getFullUserInfo();
//
//        Optional<InternPlanDetailEntity> internPlanDetailEntity = internPlanDetailRepository.findById(dto.getInternDetailId());
//        if (!internPlanDetailEntity.isPresent()) {
//            throw new CustomException(ApiStatus.NOT_FOUND);
//        }
//
//        Optional<InternPlanEntity> internPlanEntity = internPlanRepository.findById(internPlanDetailEntity.get().getInternPlanSeq());
//        if (!internPlanEntity.isPresent()) {
//            throw new CustomException(ApiStatus.NOT_FOUND);
//        }
//
//        ReportWeekStudentEntity reportWeekEntity = new ReportWeekStudentEntity();
//        reportWeekEntity.setInternDetailSeq(dto.getInternDetailId());
//        reportWeekEntity.setInternStdSeq(dto.getInternStdSeq());
//        reportWeekEntity.setDateWeek(dto.getDateWeek());
//        reportWeekEntity.setInHour(dto.getInHour());
//        reportWeekEntity.setInMinute(dto.getInMinute());
//        reportWeekEntity.setOutHour(dto.getOutHour());
//        reportWeekEntity.setOutMinute(dto.getOutMinute());
//        reportWeekEntity.setJobContent(dto.getJobContent());
//        reportWeekEntity.setAcquiredKnowledge(dto.getAcquiredKnowledge());
//        reportWeekEntity.setDelYn(Constants.STATE_N);
//
//        reportWeekRepository.save(reportWeekEntity);
//
//        return reportWeekEntity.getStdWeekReportSeq();
//    }

    private ReportWeekStudentEntity findReportWeekStudentEntity(List<ReportWeekStudentEntity> reportWeekStudent,
            String dateWeek) {
        for (ReportWeekStudentEntity entity : reportWeekStudent) {
            if (Utils.formatDateToString(entity.getDateWeek()).equals(dateWeek)) {
                return entity;
            }
        }
        return null;
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
