package com.winwintek.web.backend.api.dto.client.internMng;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InternMngResDTO {

    private List<InternMngListDTO> internMngList;

    private List<String> yearList;

}
