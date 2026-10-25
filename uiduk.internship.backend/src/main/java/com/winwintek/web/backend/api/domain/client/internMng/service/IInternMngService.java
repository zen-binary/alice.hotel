package com.winwintek.web.backend.api.domain.client.internMng.service;

import com.winwintek.web.backend.api.dto.client.internMng.InternMngListDTO;
import com.winwintek.web.backend.api.dto.client.internMng.InternMngResDTO;
import com.winwintek.web.backend.api.dto.client.internMng.InternMngSearchDTO;

import java.util.List;

public interface IInternMngService {

    InternMngResDTO getInternList(InternMngSearchDTO internMngSearchDTO);

}
