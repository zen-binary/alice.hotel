package com.winwintek.web.backend.api.dto.admin.corporate.signed;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TripartiteAgreeReqDTO {

    private String planId;

    private List<String> lstStdId;

}
