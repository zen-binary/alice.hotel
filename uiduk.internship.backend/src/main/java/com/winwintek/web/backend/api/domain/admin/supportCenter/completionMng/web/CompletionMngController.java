package com.winwintek.web.backend.api.domain.admin.supportCenter.completionMng.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.supportCenter.completionMng.service.CompletionMngService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.completionMng.CompletionStdCountResponseDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.completionMng.CompletionStdMngRequestDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.completionMng.CompletionStdMngResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/a/completion-mng")
public class CompletionMngController {

    @Autowired
    private CompletionMngService completionMngService;

    @PostMapping ()
    public ApiResponse<Page<CompletionStdMngResponseDTO>> getPage(@RequestBody CompletionStdMngRequestDTO dto) {
        Page<CompletionStdMngResponseDTO> listDtoPage = completionMngService.getDataPageCompletion(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, listDtoPage);
    }

    @PutMapping("/complete-intern-std")
    public ApiResponse<Boolean> completeInternStd(@RequestBody List<String> lstInternStdId) {
        Boolean result = completionMngService.completeIntern(lstInternStdId);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PutMapping("/wait-complete-intern-std")
    public ApiResponse<Boolean> waitCompleteInternStd(@RequestBody List<String> lstInternStdId) {
        Boolean result = completionMngService.waitCompleteIntern(lstInternStdId);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PutMapping("/not-complete-intern-std")
    public ApiResponse<Boolean> notCompleteInternStd(@RequestBody List<String> lstInternStdId) {
        Boolean result = completionMngService.notCompleteIntern(lstInternStdId);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PutMapping("/backups-intern-std")
    public ApiResponse<Boolean> backupsInternStd(@RequestBody List<String> lstInternStdId) {
        Boolean result = completionMngService.backupsIntern(lstInternStdId);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/count-status-complete")
    public ApiResponse<CompletionStdCountResponseDTO> countStatusSomplete(@RequestBody CompletionStdMngRequestDTO dto) {
        CompletionStdCountResponseDTO result = completionMngService.getCountStatus(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }
}
