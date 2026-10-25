package com.winwintek.web.backend.api.domain.admin.corporate.internAttendanceTracking.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.corporate.internAttendanceTracking.service.InternAttendanceTrackingService;
import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.request.AttendanceTrackingCustomReqDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.request.AttendanceTrackingDayWeekReqDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.request.AttendanceTrackingReqDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.response.AttendanceTrackingDayResDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.response.AttendanceTrackingDayWeekResDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.response.AttendanceTrackingResDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.response.AttendanceTrackingStudentResDTO;
import com.winwintek.web.backend.api.entity.admin.corporate.internAttendanceTracking.AttendanceStudentEntity;
import com.winwintek.web.backend.api.entity.admin.corporate.internAttendanceTracking.AttendanceTrackingEntity;
import com.winwintek.web.backend.api.entity.admin.corporate.internAttendanceTracking.IAttendaceTrackingResponse;
import com.winwintek.web.backend.api.entity.admin.corporate.internAttendanceTracking.IAttendaceTrackingStudentResponse;
import com.winwintek.web.backend.api.entity.admin.corporate.internAttendanceTracking.IAttendanceOperationPlanResponse;
import com.winwintek.web.backend.api.repository.admin.corporate.internAttendanceTracking.AttendanceOperationPlanRepository;
import com.winwintek.web.backend.api.repository.admin.corporate.internAttendanceTracking.AttendanceStudentRepository;
import com.winwintek.web.backend.api.repository.admin.corporate.internAttendanceTracking.AttendanceTrackingRepository;
import com.winwintek.web.backend.api.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_STD_ATTENDANCE;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_STD_ATTENDANCE;

@Service
public class InternAttendanceTrackingServiceImpl implements InternAttendanceTrackingService {

    @Autowired
    private AttendanceTrackingRepository attendanceTrackingRepo;

    @Autowired
    private AttendanceOperationPlanRepository attendanceOperationPlanRepo;

    @Autowired
    private AttendanceStudentRepository attendanceStudentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SequenceGenerator sequenceGenerator;


    @Override
    public List<AttendanceTrackingResDTO> getLstAttendance(AttendanceTrackingReqDTO dto) {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(Constants.FORMAT_DATE_YYYY_MM_DD);
        DateTimeFormatter formatterDateDDMM = DateTimeFormatter.ofPattern(Constants.FORMAT_DATE_MM_DD);
        List<AttendanceTrackingResDTO> lstAttendanceTrackingDTO = new ArrayList<>();
        List<IAttendanceOperationPlanResponse> lstOperationPlan = attendanceOperationPlanRepo.getAttendanceOperationPlan(
                dto.getInternPlanSeq(), Constants.STATE_N);
        List<IAttendaceTrackingResponse> lstAttendanceTracking = attendanceTrackingRepo.getLstAttendanceTracking(dto);
        lstOperationPlan.forEach(operationPlan -> {

            AttendanceTrackingResDTO attendanceTracking = new AttendanceTrackingResDTO();

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
            List<AttendanceTrackingDayWeekResDTO> dayWeeks = new ArrayList<>();
            List<List<AttendanceTrackingDayWeekResDTO>> lstDayWeek = new ArrayList<>();
            dateListTracking.forEach(dateStr -> {
                AttendanceTrackingDayWeekResDTO dayWeek = new AttendanceTrackingDayWeekResDTO();
                LocalDate date = LocalDate.parse(dateStr, formatterDate);
                dayWeek.setDate(dateStr);

                dayWeek.setDayOfWeek((date.getDayOfWeek().getValue() % 7) + 1 + "");

                LocalDate attendanceDate = LocalDate.parse(dateStr, formatterDate);
                if (! attendanceDate.isBefore(inputDateStart) && ! attendanceDate.isAfter(inputDateEnd)) {
                    AttendanceTrackingDayResDTO dayTracking = new AttendanceTrackingDayResDTO();
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
                        AttendanceTrackingDayResDTO dayTracking = new AttendanceTrackingDayResDTO();
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
    public AttendanceTrackingStudentResDTO getAttendanceStudent(AttendanceTrackingReqDTO dto) {
        IAttendaceTrackingStudentResponse data = attendanceTrackingRepo.getAttendanceTrackingStuden(dto)
                .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));
        AttendanceTrackingStudentResDTO result = modelMapper.map(data, AttendanceTrackingStudentResDTO.class);
        return result;
    }


    @Override
    @Transactional
    public boolean setAttendanceStudent(AttendanceTrackingReqDTO dto) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_DATE_YYYY_MM_DD);
        List<AttendanceTrackingCustomReqDTO> lstAttendanceTrackingDto = dto.getLstAttendanceTracking();
        lstAttendanceTrackingDto.forEach(attendanceTracking -> {
            List<AttendanceTrackingDayWeekReqDTO> lstDayWeek = attendanceTracking.getLstDayWeek().stream()
                    .flatMap(List::stream).filter(dayWeek -> Objects.nonNull(dayWeek.getInfo())).toList();
            if (lstDayWeek.isEmpty()) {
                throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
            }
            AttendanceStudentEntity attendanceStudent = attendanceStudentRepo.findByInternPlanAndStudent(dto)
                    .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));

            List<AttendanceTrackingEntity> lstAttendanceTracking = attendanceTrackingRepo.findLstAttendanceByStd(
                    attendanceStudent.getInternStdSeq(), attendanceTracking.getInternDetailSeq(), Constants.STATE_N);

            List<AttendanceTrackingEntity> lstAttendanceTrackingNew = lstDayWeek.stream()
                    .filter(item -> lstAttendanceTracking.stream().noneMatch(
                            attendance -> formatter.format(attendance.getAttendanceDate()).equals(item.getDate())))
                    .filter(item -> Objects.nonNull(item.getInfo().getStatus())).map(item -> {
                        AttendanceTrackingEntity entity = new AttendanceTrackingEntity();
                        entity.setStatus(item.getInfo().getStatus());
                        entity.setAttendanceDate(Utils.formatDate(item.getDate()));
                        entity.setInternStdSeq(attendanceStudent.getInternStdSeq());
                        entity.setInternDetailSeq(attendanceTracking.getInternDetailSeq());
                        entity.setStdAttendanceSeq(sequenceGenerator.getNextSequence(PREFIX_STD_ATTENDANCE.toString(),
                                TABLE_STD_ATTENDANCE.toString()));
                        entity.setDelYn(Constants.STATE_N);
                        return entity;
                    }).toList();

            List<AttendanceTrackingEntity> lstAttendanceTrackingUpdate = lstDayWeek.stream()
                    .filter(item -> Objects.nonNull(
                            item.getInfo().getStdAttendanceSeq()) && lstAttendanceTracking.stream().anyMatch(
                            attendance -> formatter.format(attendance.getAttendanceDate()).equals(item.getDate())))
                    .map(item -> {
                        AttendanceTrackingEntity entity = attendanceTrackingRepo.findById(
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
