package com.alice.hotel.api.dto.common.fileMng;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class MultipleFileDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<String> fimReferKeyIdList;
    private List<String> fimSectionName;

}
