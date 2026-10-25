package com.winwintek.web.backend.api.domain.client.student.qna.service;

import com.winwintek.web.backend.api.dto.client.student.qna.*;
import org.springframework.data.domain.Page;

public interface IQNAClientService {
    Page<QNAResDTO> getQNAList(QNASearchReqDTO req);
    QNADetailResDTO getQNADetail(String id);

    QNARegResDTO createQNA(QNARegReqDTO req);

    boolean deleteQNA(String id);
}
