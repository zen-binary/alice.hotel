package com.winwintek.web.backend.api.domain.admin.notice.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.notice.service.NoticeService;
import com.winwintek.web.backend.api.dto.admin.notice.NoticeDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.notice.NoticeReqDTO;
import com.winwintek.web.backend.api.dto.admin.notice.NoticeResDTO;
import com.winwintek.web.backend.api.dto.admin.notice.NoticeSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/a/notice")
@RequiredArgsConstructor
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @PostMapping ("/list")
    public ApiResponse<Page<NoticeResDTO>> getListBoard(@RequestBody NoticeSearchDTO noticeSearchDTO) {
        Page<NoticeResDTO> results = noticeService.getListNotice(noticeSearchDTO);

        ApiResponse<Page<NoticeResDTO>> resultsDTO = new ApiResponse<>(ApiStatus.SUCCESS, results);

        return resultsDTO;
    }

    @PostMapping ("/delete")
    public ApiResponse<Boolean> delete(@RequestBody List<String> lstId) {
        Boolean result = noticeService.allRemove(lstId);

        ApiResponse<Boolean> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/create")
    public ApiResponse<String> create(@RequestBody NoticeReqDTO noticeReqDTO) throws Exception {
        String result = noticeService.createNotice(noticeReqDTO);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/detail")
    public ApiResponse<NoticeDetailResDTO> detail(@RequestBody String id) {
        NoticeDetailResDTO result = noticeService.detailNotice(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PutMapping ("/update")
    public ApiResponse<String> update(@RequestBody NoticeReqDTO noticeReqDTO) {
        String result = noticeService.updateNotice(noticeReqDTO);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

}
