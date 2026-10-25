package com.winwintek.web.backend.api.domain.admin.qna.service;

import com.winwintek.web.backend.api.dto.admin.qna.QnaDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.qna.QnaReqDTO;
import com.winwintek.web.backend.api.dto.admin.qna.QnaResDTO;
import com.winwintek.web.backend.api.dto.admin.qna.QnaSearchDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QnaService {
    Page<QnaResDTO> getListQna(QnaSearchDTO qnaSearchDTO);
    Boolean allRemove(List<String> request);
    QnaDetailResDTO detailQna(String id);
    Boolean updateQna(QnaReqDTO qnaReqDTO);
}
