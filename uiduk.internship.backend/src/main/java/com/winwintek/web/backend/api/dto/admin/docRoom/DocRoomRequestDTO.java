package com.winwintek.web.backend.api.dto.admin.docRoom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DocRoomRequestDTO {

    private String docSeq;

    private String title;

    private String useYn;

    private String content;

    private String topFixYn;

}
