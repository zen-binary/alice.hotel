package com.winwintek.web.backend.api.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class VerifyCheckUrlDTO {
    private String url;
}
