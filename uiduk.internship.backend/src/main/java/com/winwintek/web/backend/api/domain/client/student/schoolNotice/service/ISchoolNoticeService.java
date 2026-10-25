package com.winwintek.web.backend.api.domain.client.student.schoolNotice.service;

import com.winwintek.web.backend.api.dto.client.student.schoolNotice.SchoolNoticeDetailResDTO;
import com.winwintek.web.backend.api.dto.client.student.schoolNotice.SchoolNoticeListResDTO;
import com.winwintek.web.backend.api.dto.client.student.schoolNotice.SchoolNoticeReqDTO;
import com.winwintek.web.backend.api.dto.client.student.schoolNotice.SchoolNoticeResDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISchoolNoticeService {
    List<SchoolNoticeResDTO> getSchoolNoticeList();

    Page<SchoolNoticeListResDTO> getSChoolNoticeList(SchoolNoticeReqDTO request);

    SchoolNoticeDetailResDTO getSchoolNoticeDetail(String id);

}
