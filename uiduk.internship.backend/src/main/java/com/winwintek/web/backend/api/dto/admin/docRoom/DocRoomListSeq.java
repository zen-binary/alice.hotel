package com.winwintek.web.backend.api.dto.admin.docRoom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DocRoomListSeq {
    private List<DocRoomResponseDTO> docRoomResponseDTOS;
}
