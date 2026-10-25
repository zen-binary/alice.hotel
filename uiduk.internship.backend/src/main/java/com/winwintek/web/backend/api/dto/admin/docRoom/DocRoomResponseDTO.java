package com.winwintek.web.backend.api.dto.admin.docRoom;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties (ignoreUnknown = true)
public class DocRoomResponseDTO {

    private String rowNumber;

    private String docSeq;

    private String useYn;

    private String title;

    private String regId;

    private String regDate;
}
