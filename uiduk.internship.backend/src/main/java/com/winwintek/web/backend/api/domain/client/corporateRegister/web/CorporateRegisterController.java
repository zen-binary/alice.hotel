package com.winwintek.web.backend.api.domain.client.corporateRegister.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.corporateRegister.service.ICorporateRegisterService;
import com.winwintek.web.backend.api.domain.common.service.CodeMngService;
import com.winwintek.web.backend.api.dto.client.corporateRegister.CorporateInfoRegisterRequest;
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
@RequestMapping ("/c/corporate-register")
public class CorporateRegisterController {

    @Autowired
    private ICorporateRegisterService corporateRegisterService;

    @Autowired
    private CodeMngService codeMngService;


    @PostMapping ()
    public ApiResponse<String> corporateRegister(@RequestBody CorporateInfoRegisterRequest request) {
        String result = corporateRegisterService.createCorporate(request);
        return new ApiResponse<>(ApiStatus.CREATED, result);
    }

    @PostMapping ("/check-register")
    public ApiResponse<Boolean> corporateRegister(@RequestBody String userId) {
        boolean result = corporateRegisterService.isExistRegister(userId);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }


    @PostMapping("/list-code")
    public ApiResponse<List<CodeMngDTO>> getCodeByUpIdList(@RequestBody CodeMngResListDTO codeMngResDTO) {
        List<CodeMngDTO> codeMngDTOS = codeMngService.getListCode(codeMngResDTO);

        return new ApiResponse<>(ApiStatus.SUCCESS, codeMngDTOS);
    }


}
