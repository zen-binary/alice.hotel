package com.winwintek.web.backend.api.domain.client.professor.attendanceTrackingLecturer.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.domain.client.professor.attendanceTrackingLecturer.service.AttendanceTrackingLecturerService;
import com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.request.AttendanceTrackingLecturerReqDTO;
import com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.response.AttendanceTrackingDayLecturerResDTO;
import com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.response.AttendanceTrackingDayWeekLecturerResDTO;
import com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.response.AttendanceTrackingLecturerResDTO;
import com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.response.AttendanceTrackingStudentLecturerResDTO;
import com.winwintek.web.backend.api.entity.client.professor.attendanceTrackingLecturer.IAttendanceOperationPlanLecturerResponse;
import com.winwintek.web.backend.api.entity.client.professor.attendanceTrackingLecturer.IAttendanceTrackingLecturerResponse;
import com.winwintek.web.backend.api.entity.client.professor.attendanceTrackingLecturer.IAttendanceTrackingStudentLecturerResponse;
import com.winwintek.web.backend.api.repository.client.professor.attendanceTrackingLecturer.AttendanceOperationPlanLecturerRepository;
import com.winwintek.web.backend.api.repository.client.professor.attendanceTrackingLecturer.AttendanceTrackingLecturerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class AttendanceTrackingLecturerServiceImpl implements AttendanceTrackingLecturerService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AttendanceOperationPlanLecturerRepository attendanceOperationPlanRepo;

    @Autowired
    private AttendanceTrackingLecturerRepository attendanceTrackingRepo;

    @Override
    public List<AttendanceTrackingLecturerResDTO> getLstAttendance(AttendanceTrackingLecturerReqDTO dto) {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(Constants.FORMAT_DATE_YYYY_MM_DD);
        DateTimeFormatter formatterDateDDMM = DateTimeFormatter.ofPattern(Constants.FORMAT_DATE_MM_DD);
        List<AttendanceTrackingLecturerResDTO> lstAttendanceTrackingDTO = new ArrayList<>();
        List<IAttendanceOperationPlanLecturerResponse> lstOperationPlan = attendanceOperationPlanRepo.getAttendanceOperationPlan(
                dto.getInternPlanSeq(), Constants.STATE_N);
        List<IAttendanceTrackingLecturerResponse> lstAttendanceTracking = attendanceTrackingRepo.getLstAttendanceTracking(dto);
        lstOperationPlan.forEach(operationPlan -> {

            AttendanceTrackingLecturerResDTO attendanceTracking = new AttendanceTrackingLecturerResDTO();

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
            List<AttendanceTrackingDayWeekLecturerResDTO> dayWeeks = new ArrayList<>();
            List<List<AttendanceTrackingDayWeekLecturerResDTO>> lstDayWeek = new ArrayList<>();
            dateListTracking.forEach(dateStr -> {
                AttendanceTrackingDayWeekLecturerResDTO dayWeek = new AttendanceTrackingDayWeekLecturerResDTO();
                LocalDate date = LocalDate.parse(dateStr, formatterDate);
                dayWeek.setDate(dateStr);
                dayWeek.setDayOfWeek((date.getDayOfWeek().getValue() % 7) + 1 + "");


                LocalDate attendanceDate = LocalDate.parse(dateStr, formatterDate);
                if (! attendanceDate.isBefore(inputDateStart) && ! attendanceDate.isAfter(inputDateEnd)) {
                    AttendanceTrackingDayLecturerResDTO dayTracking = new AttendanceTrackingDayLecturerResDTO();
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
                        AttendanceTrackingDayLecturerResDTO dayTracking = new AttendanceTrackingDayLecturerResDTO();
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
    public AttendanceTrackingStudentLecturerResDTO getAttendanceStudent(AttendanceTrackingLecturerReqDTO dto) {
        IAttendanceTrackingStudentLecturerResponse data = attendanceTrackingRepo.getAttendanceTrackingStudent(dto)
                .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_VALID));
        AttendanceTrackingStudentLecturerResDTO result = modelMapper.map(data, AttendanceTrackingStudentLecturerResDTO.class);
        return result;
    }

}
