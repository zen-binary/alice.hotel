package com.winwintek.web.backend.api.dto.admin.supportCenter.completionMng;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletionStdCountResponseDTO {

    private String waitCompleteCount;

    private String completeCount;

    private String notCompleteCount;

    private String backupsCount;
}
