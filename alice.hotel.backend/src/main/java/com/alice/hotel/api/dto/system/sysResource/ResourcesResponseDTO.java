package com.alice.hotel.api.dto.system.sysResource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResourcesResponseDTO {

    private Long rowNumber;
    private Long rsId;
    private String url;
    private String httpMethod;
    private String rsNm;
    private String rsType;
    private Date regDate;

}
