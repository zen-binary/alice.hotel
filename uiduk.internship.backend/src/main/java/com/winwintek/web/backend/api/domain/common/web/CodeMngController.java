package com.winwintek.web.backend.api.domain.common.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.common.service.CodeMngService;
import com.winwintek.web.backend.api.dto.common.codeMng.CodeMngDTO;
import com.winwintek.web.backend.api.dto.common.codeMng.CodeMngResDTO;
import com.winwintek.web.backend.api.dto.common.codeMng.CodeMngResListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/code")
public class CodeMngController {

    @Autowired
    private CodeMngService codeMngService;

    /**
     * 코드 검색
     *
     * @param upCdId
     * @return
     */
    @PostMapping("/")
    public ApiResponse<List<CodeMngDTO>> getCodeByUpId(@RequestBody CodeMngResDTO codeMngResDTO) {
        List<CodeMngDTO> codeMngDTOS = codeMngService.getByUpId(codeMngResDTO);

        return new ApiResponse<>(ApiStatus.SUCCESS, codeMngDTOS);
    }

    /**
     * 코드 검색
     *
     * @param upCdId
     * @return
     */
    @PostMapping("/listCode")
    public ApiResponse<List<CodeMngDTO>> getCodeByUpIdList(@RequestBody CodeMngResListDTO codeMngResDTO) {
        List<CodeMngDTO> codeMngDTOS = codeMngService.getListCode(codeMngResDTO);

        return new ApiResponse<>(ApiStatus.SUCCESS, codeMngDTOS);
    }
}
