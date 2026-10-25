package com.alice.hotel.api.domain.common.service;

import com.alice.hotel.api.dto.common.codeMng.CodeMngDTO;
import com.alice.hotel.api.dto.common.codeMng.CodeMngResDTO;
import com.alice.hotel.api.dto.common.codeMng.CodeMngResListDTO;

import java.util.List;


public interface CodeMngService {
    List<CodeMngDTO> getByUpId(CodeMngResDTO codeMngResDTO);

    List<CodeMngDTO> getListCode(CodeMngResListDTO codeMngResDTO);
}
