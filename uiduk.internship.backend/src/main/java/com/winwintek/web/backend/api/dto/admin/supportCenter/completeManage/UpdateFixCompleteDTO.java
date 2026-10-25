package com.winwintek.web.backend.api.dto.admin.supportCenter.completeManage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFixCompleteDTO {
    private String status;
    private List<String> lstId;
}
