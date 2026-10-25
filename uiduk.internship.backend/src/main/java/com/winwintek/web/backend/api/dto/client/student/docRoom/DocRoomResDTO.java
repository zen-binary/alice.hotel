package com.winwintek.web.backend.api.dto.client.student.docRoom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DocRoomResDTO {
    private int rowNumber;
    private String docSeq;
    private String title;
    private String regDate;
    private String topFixYn;
}
