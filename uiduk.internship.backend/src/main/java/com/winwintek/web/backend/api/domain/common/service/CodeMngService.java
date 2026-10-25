package com.winwintek.web.backend.api.domain.common.service;

import com.winwintek.web.backend.api.dto.common.codeMng.CodeMngDTO;
import com.winwintek.web.backend.api.dto.common.codeMng.CodeMngResDTO;
import com.winwintek.web.backend.api.dto.common.codeMng.CodeMngResListDTO;

import java.util.List;


public interface CodeMngService {
    List<CodeMngDTO> getByUpId(CodeMngResDTO codeMngResDTO);

    List<CodeMngDTO> getListCode(CodeMngResListDTO codeMngResDTO);
}
