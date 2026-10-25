package com.winwintek.web.backend.api.domain.client.student.internStudent.service;

import com.winwintek.web.backend.api.dto.client.student.internStudent.InternStudentListResDTO;
import com.winwintek.web.backend.api.dto.client.student.internStudent.InternStudentRegResDTO;
import com.winwintek.web.backend.api.dto.client.student.internStudent.InternStudentReqDTO;

import java.util.List;

public interface IInternStudentService {
    InternStudentRegResDTO createInternStudent(String id);

    List<InternStudentListResDTO> getInternStudentList(InternStudentReqDTO req);

    boolean updateInternStudentApproStatus(String id);

    List<String> getInternshipYearAvailableByStudent(String id);
}
