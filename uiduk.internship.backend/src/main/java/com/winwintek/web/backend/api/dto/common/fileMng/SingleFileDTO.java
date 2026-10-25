package com.winwintek.web.backend.api.dto.common.fileMng;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class SingleFileDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String encodedFileName;

}
