package com.winwintek.web.backend.api.domain.client.student.qna.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.student.qna.service.IQNAClientService;
import com.winwintek.web.backend.api.dto.client.student.qna.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("c/qna")
public class QNAClientController {

    @Autowired
    private IQNAClientService iqnaClientService;

    @PostMapping("/")
    public ApiResponse<Page<QNAResDTO>> getQNAList(@RequestBody QNASearchReqDTO req){
        Page<QNAResDTO> response = iqnaClientService.getQNAList(req);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }

    @PostMapping("/detail")
    public ApiResponse<QNADetailResDTO> getQNADetail(@RequestBody String id){
        QNADetailResDTO response = iqnaClientService.getQNADetail(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }

    @PostMapping("/create")
    public ApiResponse<QNARegResDTO> createQNA(@RequestBody QNARegReqDTO req){
        QNARegResDTO response = iqnaClientService.createQNA(req);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }

    @DeleteMapping("/delete")
    public ApiResponse<Boolean> deleteQNA(@RequestBody String id){
        boolean response = iqnaClientService.deleteQNA(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }
}
