package com.alice.hotel.api.dto.common.codeMng;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CodeMngResListDTO {
    private List<String> upCdIdList;
}