package com.winwintek.web.backend.api.domain.admin.qna.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.qna.service.QnaService;
import com.winwintek.web.backend.api.dto.admin.qna.QnaDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.qna.QnaReqDTO;
import com.winwintek.web.backend.api.dto.admin.qna.QnaResDTO;
import com.winwintek.web.backend.api.dto.admin.qna.QnaSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/a/qna")
@RequiredArgsConstructor
public class QnaController {
    @Autowired
    private QnaService qnaService;

    @PostMapping ("/list")
    public ApiResponse<Page<QnaResDTO>> getListBoard(@RequestBody QnaSearchDTO qnaSearchDTO) {
        Page<QnaResDTO> results = qnaService.getListQna(qnaSearchDTO);

        ApiResponse<Page<QnaResDTO>> resultsDTO = new ApiResponse<>(ApiStatus.SUCCESS, results);

        return resultsDTO;
    }

    @PostMapping ("/delete")
    public ApiResponse<Boolean> delete(@RequestBody List<String> lstId) {
        Boolean result = qnaService.allRemove(lstId);

        ApiResponse<Boolean> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/detail")
    public ApiResponse<QnaDetailResDTO> detail(@RequestBody String id) {
        QnaDetailResDTO result = qnaService.detailQna(id);
        return new ApiResponse<QnaDetailResDTO>(ApiStatus.SUCCESS, result);
    }

    @PutMapping ("/update")
    public ApiResponse<Boolean> update(@RequestBody QnaReqDTO qnaReqDTO) {
        Boolean result = qnaService.updateQna(qnaReqDTO);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }
}
