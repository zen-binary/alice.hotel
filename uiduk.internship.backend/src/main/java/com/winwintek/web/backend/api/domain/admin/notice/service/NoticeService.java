package com.winwintek.web.backend.api.domain.admin.notice.service;

import com.winwintek.web.backend.api.dto.admin.notice.NoticeDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.notice.NoticeReqDTO;
import com.winwintek.web.backend.api.dto.admin.notice.NoticeResDTO;
import com.winwintek.web.backend.api.dto.admin.notice.NoticeSearchDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoticeService {

    String createNotice(NoticeReqDTO noticeReqDTO);

    Page<NoticeResDTO> getListNotice(NoticeSearchDTO noticeSearchDTO);

    Boolean allRemove(List<String> request);

    NoticeDetailResDTO detailNotice(String id);

    String updateNotice(NoticeReqDTO noticeReqDTO);

}
