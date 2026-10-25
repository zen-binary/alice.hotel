package com.winwintek.web.backend.api.domain.admin.corporate.operateInternMng.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.corporate.operateInternMng.service.OperateInternCorpService;
import com.winwintek.web.backend.api.dto.admin.corporate.operateInternMng.OperateInternCorpResDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.operateInternMng.OperateInternCorpSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/a/c/operate-intern")
public class OperateInternCorpController {

    @Autowired
    private OperateInternCorpService operateInternCorpService;

    @PostMapping ("/list")
    public ApiResponse<Page<OperateInternCorpResDTO>> findListOperateInternCorp(
            @RequestBody OperateInternCorpSearchDTO searchDTO) {
        Page<OperateInternCorpResDTO> lstResult = operateInternCorpService.findListOperateInternCorp(searchDTO);

        ApiResponse<Page<OperateInternCorpResDTO>> lstResultDTO = new ApiResponse<>(ApiStatus.SUCCESS, lstResult);

        return lstResultDTO;
    }

}
