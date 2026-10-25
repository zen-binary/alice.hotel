package com.winwintek.web.backend.api.dto.client.student.docRoom;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DocRoomReqDTO extends PageableDto {
    private String title;
}
