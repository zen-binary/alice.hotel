package com.winwintek.web.backend.api.dto.common.fileMng;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FileUploadedInfoDto {

    private String fimId ;
    private String fimFileName ;
    private String fimOrgName ;
    private String fimFilePath ;
    private Long fimFileSize ;
    private String deleteFlag ;
    private String urlFile;
}
