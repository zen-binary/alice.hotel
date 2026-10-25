package com.winwintek.web.backend.api.domain.client.student.docRoom.service;

import com.winwintek.web.backend.api.dto.client.student.docRoom.DocRoomDetailResDTO;
import com.winwintek.web.backend.api.dto.client.student.docRoom.DocRoomReqDTO;
import com.winwintek.web.backend.api.dto.client.student.docRoom.DocRoomResDTO;
import org.springframework.data.domain.Page;


public interface IDocRoomService {
    Page<DocRoomResDTO> getSChoolNoticeList(DocRoomReqDTO request);

    DocRoomDetailResDTO getSchoolNoticeDetail(String id);

}
