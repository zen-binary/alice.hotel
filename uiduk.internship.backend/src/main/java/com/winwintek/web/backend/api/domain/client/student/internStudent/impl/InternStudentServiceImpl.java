package com.winwintek.web.backend.api.domain.client.student.internStudent.impl;

import com.winwintek.web.backend.api.config.file.service.FileMngService;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.client.student.internStudent.service.IInternStudentService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.client.student.internStudent.InternStudentListResDTO;
import com.winwintek.web.backend.api.dto.client.student.internStudent.InternStudentRegResDTO;
import com.winwintek.web.backend.api.dto.client.student.internStudent.InternStudentReqDTO;
import com.winwintek.web.backend.api.dto.common.fileMng.FileDto;
import com.winwintek.web.backend.api.dto.common.fileMng.FileUploadedInfoDto;
import com.winwintek.web.backend.api.entity.client.student.internStudent.IInternStudentRegOutput;
import com.winwintek.web.backend.api.entity.client.student.internStudent.InternStudentEntity;
import com.winwintek.web.backend.api.repository.client.student.internStudent.InternStudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.winwintek.web.backend.api.common.Constants.*;
import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_INTERN_STD;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_INTERN_STD;

@Service
public class InternStudentServiceImpl implements IInternStudentService {

    public static String APPROVE_STATUS_WAIT_APPROVE = "STD_A01_P01";

    public static String APPROVE_STATUS_CANCEL = "STD_A01_P08";

    @Autowired
    private InternStudentRepository internStudentRepository;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileMngService fileMngService;

    @Override
    @Transactional
    public InternStudentRegResDTO createInternStudent(String internPlanSeq) {
        String stdNo = commonService.getFullUserInfo().getUserId();

        List<InternStudentEntity> internStudentEntities = internStudentRepository.findByStdNoAndInternPlanSeqAndDelYn(
                stdNo, internPlanSeq, STATE_N);
        boolean hasApprovedP01 = false;
        boolean hasApprovedP08 = false;

        for (InternStudentEntity entity : internStudentEntities) {
            String approveStatus = entity.getApproveStatus();

            if (APPROVE_STATUS_WAIT_APPROVE.equals(approveStatus)) {
                hasApprovedP01 = true;
                break;
            }

            if (APPROVE_STATUS_CANCEL.equals(approveStatus)) {
                hasApprovedP08 = true;
            }
        }

        if (hasApprovedP01) {
            return null;
        }

        if (hasApprovedP08) {
            InternStudentEntity newInternStudent = new InternStudentEntity();
            newInternStudent.setInternStdSeq(
                    sequenceGenerator.getNextSequence(PREFIX_INTERN_STD.toString(), TABLE_INTERN_STD.toString()));
            newInternStudent.setStdNo(stdNo);
            newInternStudent.setInternPlanSeq(internPlanSeq);
            newInternStudent.setApproveStatus(INTERN_STUDENT_APPROVE_STATUS_PENDING);
            newInternStudent.setInternshipStatus(INTERN_STUDENT_INTERNSHIP_STATUS_DEFAULT);
            newInternStudent.setDelYn(STATE_N);

            internStudentRepository.save(newInternStudent);

            return modelMapper.map(newInternStudent, InternStudentRegResDTO.class);
        }

        InternStudentEntity internStudent = new InternStudentEntity();
        internStudent.setInternStdSeq(
                sequenceGenerator.getNextSequence(PREFIX_INTERN_STD.toString(), TABLE_INTERN_STD.toString()));
        internStudent.setStdNo(stdNo);
        internStudent.setInternPlanSeq(internPlanSeq);
        internStudent.setApproveStatus(INTERN_STUDENT_APPROVE_STATUS_PENDING);
        internStudent.setInternshipStatus(INTERN_STUDENT_INTERNSHIP_STATUS_DEFAULT);
        internStudent.setDelYn(STATE_N);

        internStudentRepository.save(internStudent);

        return modelMapper.map(internStudent, InternStudentRegResDTO.class);
    }

    @Override
    public List<InternStudentListResDTO> getInternStudentList(InternStudentReqDTO req) {

        String regId = commonService.getFullUserInfo().getUserId();

        String fimFileCategory = INTERN_STUDENT_FILE_CATEGORY;
        String fimSectionName = INTERN_STUDENT_SECTION_NAME;

        List<IInternStudentRegOutput> internList = internStudentRepository.getInternStudentList(req, STATE_N, regId);
        List<InternStudentListResDTO> listResDTOS = internList.stream().map(item -> {
            InternStudentListResDTO dto = modelMapper.map(item, InternStudentListResDTO.class);
            String urlFile = getUrlFile(item.getInternPlanSeq(), fimFileCategory, fimSectionName);
            dto.setUrlFile(urlFile);
            return dto;
        }).collect(Collectors.toList());
        return listResDTOS;
    }

    @Override
    @Transactional
    public boolean updateInternStudentApproStatus(String id) {
        Optional<InternStudentEntity> currentInternStudent = internStudentRepository.findByInternStdSeq(id);
        int rowAffected = 0;
        if (currentInternStudent != null) {
            rowAffected = internStudentRepository.updateByApproveStatus(id, INTERN_STUDENT_APPROVE_STATUS_CANCEL);
        }
        return rowAffected > 0;
    }

    @Override
    public List<String> getInternshipYearAvailableByStudent(String id) {
        id = commonService.getFullUserInfo().getUserId();
        return internStudentRepository.findAvailableInternshipYearsByStudent(id, STATE_N);
    }

    private String getUrlFile(String fimReferKeyId, String fimFileCategory, String fimSectionName) {
        FileDto dto = new FileDto();
        dto.setFimReferKeyId(fimReferKeyId);
        dto.setFimFileCategory(fimFileCategory);
        dto.setFimSectionName(fimSectionName);
        List<FileUploadedInfoDto> lstFiles = fileMngService.selectFileUploaded(dto);
        if (! lstFiles.isEmpty()) {
            return lstFiles.getFirst().getUrlFile();
        }
        return null;
    }

}
