package com.winwintek.web.backend.api.dto.admin.supportCenter.agreement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateListAppendSupportCenterDTO {
    private List<String> lstId;
}
