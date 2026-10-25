package com.winwintek.web.backend.api.domain.admin.supportCenter.completionMng.service;

import com.winwintek.web.backend.api.dto.admin.supportCenter.completionMng.CompletionStdCountResponseDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.completionMng.CompletionStdMngRequestDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.completionMng.CompletionStdMngResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompletionMngService {

    Page<CompletionStdMngResponseDTO> getDataPageCompletion(CompletionStdMngRequestDTO dto);

    boolean waitCompleteIntern(List<String> lstInternStd);

    boolean completeIntern(List<String> lstInternStd);

    boolean notCompleteIntern(List<String> lstInternStd);

    boolean backupsIntern(List<String> lstInternStd);

    CompletionStdCountResponseDTO getCountStatus(CompletionStdMngRequestDTO dto);

}
