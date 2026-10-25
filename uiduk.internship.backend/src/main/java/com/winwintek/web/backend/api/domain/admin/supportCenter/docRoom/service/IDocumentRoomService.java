package com.winwintek.web.backend.api.domain.admin.supportCenter.docRoom.service;

import com.winwintek.web.backend.api.dto.admin.docRoom.DocRoomRequestDTO;
import com.winwintek.web.backend.api.dto.admin.docRoom.DocRoomResponseDTO;
import com.winwintek.web.backend.api.dto.admin.docRoom.DocRoomSearchDTO;
import com.winwintek.web.backend.api.dto.admin.docRoom.DocRoomSeq;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDocumentRoomService {

    Page<DocRoomResponseDTO> getAllDocRooms(DocRoomSearchDTO docRoomSearchDTO);

    String createDocRoom(DocRoomRequestDTO docRoomRequestDTO);

    DocRoomRequestDTO detailDocRoom(DocRoomSeq docSeq);

    Boolean updDocRoom(DocRoomRequestDTO docRoomRequestDTO);

    Boolean deleteDocRoom(List<DocRoomResponseDTO> docRoomResponseDTOS);

}
