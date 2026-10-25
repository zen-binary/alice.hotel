package com.winwintek.web.backend.api.domain.admin.attendanceTrackingCenter.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.domain.admin.attendanceTrackingCenter.service.AttendanceTrackingCenterService;
import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.request.AttendanceTrackingCenterReqDTO;
import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.request.AttendanceTrackingCustomCenterReqDTO;
import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.request.AttendanceTrackingDayWeekCenterReqDTO;
import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.response.AttendanceTrackingDayCenterResDTO;
import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.response.AttendanceTrackingDayWeekCenterResDTO;
import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.response.AttendanceTrackingCenterResDTO;
import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.response.AttendanceTrackingStudentCenterResDTO;
import com.winwintek.web.backend.api.entity.admin.attendanceTrackingCenter.AttendanceStudentCenterEntity;
import com.winwintek.web.backend.api.entity.admin.attendanceTrackingCenter.AttendanceTrackingCenterEntity;
import com.winwintek.web.backend.api.entity.admin.attendanceTrackingCenter.IAttendaceTrackingCenterResponse;
import com.winwintek.web.backend.api.entity.admin.attendanceTrackingCenter.IAttendaceTrackingStudentCenterResponse;
import com.winwintek.web.backend.api.entity.admin.attendanceTrackingCenter.IAttendanceOperationPlanCenterResponse;
import com.winwintek.web.backend.api.repository.admin.attendanceTrackingCenter.AttendanceOperationPlanCenterRepository;
import com.winwintek.web.backend.api.repository.admin.attendanceTrackingCenter.AttendanceStudentCenterRepository;
import com.winwintek.web.backend.api.repository.admin.attendanceTrackingCenter.AttendanceTrackingCenterRepository;
import com.winwintek.web.backend.api.util.Utils;
import org.springframework.stereotype.Service;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_STD_ATTENDANCE;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_STD_ATTENDANCE;

@Service
public class AttendanceTrackingCenterServiceImpl implements AttendanceTrackingCenterService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private AttendanceOperationPlanCenterRepository attendanceOperationPlanRepo;

    @Autowired
    private AttendanceTrackingCenterRepository attendanceTrackingRepo;

    @Autowired
    private AttendanceStudentCenterRepository attendanceStudentRepo;

    @Override
    public List<AttendanceTrackingCenterResDTO> getLstAttendance(AttendanceTrackingCenterReqDTO dto) {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(Constants.FORMAT_DATE_YYYY_MM_DD);
        DateTimeFormatter formatterDateDDMM = DateTimeFormatter.ofPattern(Constants.FORMAT_DATE_MM_DD);
        List<AttendanceTrackingCenterResDTO> lstAttendanceTrackingDTO = new ArrayList<>();
        List<IAttendanceOperationPlanCenterResponse> lstOperationPlan = attendanceOperationPlanRepo.getAttendanceOperationPlan(
                dto.getInternPlanSeq(), Constants.STATE_N);
        List<IAttendaceTrackingCenterResponse> lstAttendanceTracking = attendanceTrackingRepo.getLstAttendanceTracking(dto);
        lstOperationPlan.forEach(operationPlan -> {

            AttendanceTrackingCenterResDTO attendanceTracking = new AttendanceTrackingCenterResDTO();

            LocalDate inputDateStart = LocalDate.parse(operationPlan.getStartDate(), formatterDate);
            LocalDate inputDateEnd = LocalDate.parse(operationPlan.getEndDate(), formatterDate);
            LocalDate startOfWeek = inputDateStart.with(DayOfWeek.MONDAY);
            LocalDate endOfWeek = inputDateEnd.with(DayOfWeek.SUNDAY);

            List<String> dateListTracking = new ArrayList<>();
            while (! startOfWeek.isAfter(endOfWeek)) {
                dateListTracking.add(startOfWeek.format(formatterDate));
                startOfWeek = startOfWeek.plusDays(1);
            }
            Set<Integer> lstYear = new HashSet<>();
            List<AttendanceTrackingDayWeekCenterResDTO> dayWeeks = new ArrayList<>();
            List<List<AttendanceTrackingDayWeekCenterResDTO>> lstDayWeek = new ArrayList<>();
            dateListTracking.forEach(dateStr -> {
                AttendanceTrackingDayWeekCenterResDTO dayWeek = new AttendanceTrackingDayWeekCenterResDTO();
                LocalDate date = LocalDate.parse(dateStr, formatterDate);
                dayWeek.setDate(dateStr);
                dayWeek.setDayOfWeek((date.getDayOfWeek().getValue() % 7) + 1 + "");


                LocalDate attendanceDate = LocalDate.parse(dateStr, formatterDate);
                if (! attendanceDate.isBefore(inputDateStart) && ! attendanceDate.isAfter(inputDateEnd)) {
                    AttendanceTrackingDayCenterResDTO dayTracking = new AttendanceTrackingDayCenterResDTO();
                    dayTracking.setDate(attendanceDate.format(formatterDateDDMM));
                    dayWeek.setInfo(dayTracking);
                    lstYear.add(date.getYear());
                }

                List<String> daysList = Arrays.asList(operationPlan.getInternshipDay().split(","));
                if (! daysList.contains(dayWeek.getDayOfWeek())) {
                    dayWeek.setInfo(null);
                }

                lstAttendanceTracking.forEach(item -> {
                    if (dateStr.equals(item.getAttendanceDate()) && item.getInternDetailSeq().equals(operationPlan.getInternDetailSeq())) {
                        AttendanceTrackingDayCenterResDTO dayTracking = new AttendanceTrackingDayCenterResDTO();
                        dayTracking.setDate(
                                LocalDate.parse(item.getAttendanceDate(), formatterDate).format(formatterDateDDMM));
                        dayTracking.setStatus(item.getStatus());
                        dayTracking.setStdAttendanceSeq(item.getStdAttendanceSeq());
                        dayWeek.setInfo(dayTracking);
                    }
                });

                dayWeeks.add(dayWeek);
                if (dayWeeks.size() == 7) {
                    lstDayWeek.add(new ArrayList<>(dayWeeks));
                    dayWeeks.clear();
                }
            });
            attendanceTracking.setLstDayWeek(lstDayWeek);
            attendanceTracking.setWeek(operationPlan.getWeek());

            String result = lstYear.stream().sorted().map(String::valueOf)
                    .collect(Collectors.joining(lstYear.size() == 1 ? "" : "/"));
            attendanceTracking.setYear(result);

            Long countAttendance = lstDayWeek.stream().flatMap(List::stream)
                    .filter(dayWeek -> Objects.nonNull(dayWeek) && Objects.nonNull(
                            dayWeek.getInfo()) && Objects.nonNull(dayWeek.getInfo().getStatus()) && (dayWeek.getInfo()
                            .getStatus().equalsIgnoreCase(Constants.CD_STD_ATTENDANCE) || dayWeek.getInfo().getStatus()
                            .equalsIgnoreCase(Constants.CD_STD_TARDINESS))).count();
            Long countAbsence = lstDayWeek.stream().flatMap(List::stream)
                    .filter(dayWeek -> Objects.nonNull(dayWeek) && Objects.nonNull(
                            dayWeek.getInfo()) && Objects.nonNull(dayWeek.getInfo().getStatus()) && dayWeek.getInfo()
                            .getStatus().equalsIgnoreCase(Constants.CD_STD_ABSENCE)).count();
            Long countHoliday = lstDayWeek.stream().flatMap(List::stream)
                    .filter(dayWeek -> Objects.nonNull(dayWeek) && Objects.nonNull(
                            dayWeek.getInfo()) && Objects.nonNull(dayWeek.getInfo().getStatus()) && dayWeek.getInfo()
                            .getStatus().equalsIgnoreCase(Constants.CD_STD_HOLIDAY)).count();
            attendanceTracking.setAttendance(countAttendance);
            attendanceTracking.setAbsence(countAbsence);
            attendanceTracking.setHoliday(countHoliday);

            attendanceTracking.setInternDetailSeq(operationPlan.getInternDetailSeq());
            lstAttendanceTrackingDTO.add(attendanceTracking);

        });

        return lstAttendanceTrackingDTO;
    }

    @Override
    public AttendanceTrackingStudentCenterResDTO getAttendanceStudent(AttendanceTrackingCenterReqDTO dto) {
        IAttendaceTrackingStudentCenterResponse data = attendanceTrackingRepo.getAttendanceTrackingStudent(dto)
                .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));
        AttendanceTrackingStudentCenterResDTO result = modelMapper.map(data, AttendanceTrackingStudentCenterResDTO.class);
        return result;
    }

    @Override
    @Transactional
    public boolean setAttendanceStudent(AttendanceTrackingCenterReqDTO dto) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_DATE_YYYY_MM_DD);
        List<AttendanceTrackingCustomCenterReqDTO> lstAttendanceTrackingDto = dto.getLstAttendanceTracking();
        lstAttendanceTrackingDto.forEach(attendanceTracking -> {
            List<AttendanceTrackingDayWeekCenterReqDTO> lstDayWeek = attendanceTracking.getLstDayWeek().stream()
                    .flatMap(List::stream).filter(dayWeek -> Objects.nonNull(dayWeek.getInfo())).toList();
            if (lstDayWeek.isEmpty()) {
                throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
            }
            AttendanceStudentCenterEntity attendanceStudent = attendanceStudentRepo.findByInternPlanAndStudent(dto)
                    .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));

            List<AttendanceTrackingCenterEntity> lstAttendanceTracking = attendanceTrackingRepo.findLstAttendanceByStd(
                    attendanceStudent.getInternStdSeq(), attendanceTracking.getInternDetailSeq(), Constants.STATE_N);

            List<AttendanceTrackingCenterEntity> lstAttendanceTrackingNew = lstDayWeek.stream()
                    .filter(item -> lstAttendanceTracking.stream().noneMatch(
                            attendance -> formatter.format(attendance.getAttendanceDate()).equals(item.getDate())))
                    .filter(item -> Objects.nonNull(item.getInfo().getStatus())).map(item -> {
                        AttendanceTrackingCenterEntity entity = new AttendanceTrackingCenterEntity();
                        entity.setStatus(item.getInfo().getStatus());
                        entity.setAttendanceDate(Utils.formatDate(item.getDate()));
                        entity.setInternStdSeq(attendanceStudent.getInternStdSeq());
                        entity.setInternDetailSeq(attendanceTracking.getInternDetailSeq());
                        entity.setStdAttendanceSeq(sequenceGenerator.getNextSequence(PREFIX_STD_ATTENDANCE.toString(),
                                TABLE_STD_ATTENDANCE.toString()));
                        entity.setDelYn(Constants.STATE_N);
                        return entity;
                    }).toList();

            List<AttendanceTrackingCenterEntity> lstAttendanceTrackingUpdate = lstDayWeek.stream()
                    .filter(item -> Objects.nonNull(
                            item.getInfo().getStdAttendanceSeq()) && lstAttendanceTracking.stream().anyMatch(
                            attendance -> formatter.format(attendance.getAttendanceDate()).equals(item.getDate())))
                    .map(item -> {
                        AttendanceTrackingCenterEntity entity = attendanceTrackingRepo.findById(
                                        item.getInfo().getStdAttendanceSeq())
                                .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));
                        entity.setStatus(item.getInfo().getStatus());
                        return entity;
                    }).toList();

            attendanceTrackingRepo.saveAll(lstAttendanceTrackingNew);
            attendanceTrackingRepo.saveAll(lstAttendanceTrackingUpdate);
        });
        return true;
    }

}
