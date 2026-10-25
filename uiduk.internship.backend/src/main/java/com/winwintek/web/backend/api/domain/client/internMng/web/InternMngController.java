package com.winwintek.web.backend.api.domain.client.internMng.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.internMng.service.IInternMngService;
import com.winwintek.web.backend.api.dto.client.internMng.InternMngListDTO;
import com.winwintek.web.backend.api.dto.client.internMng.InternMngResDTO;
import com.winwintek.web.backend.api.dto.client.internMng.InternMngSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/p/intern-mng")
@RequiredArgsConstructor
public class InternMngController {

    @Autowired
    private IInternMngService internMngService;

    @PostMapping(value = "/list")
    public ApiResponse<InternMngResDTO> getInternList (@RequestBody InternMngSearchDTO internMngSearchDTO) {
        InternMngResDTO dataInternList = internMngService.getInternList(internMngSearchDTO);
        return new ApiResponse<>(ApiStatus.SUCCESS, dataInternList);
    }

}
