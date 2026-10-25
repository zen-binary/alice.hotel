package com.winwintek.web.backend.api.domain.admin.supportCenter.docRoom.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.supportCenter.docRoom.service.IDocumentRoomService;
import com.winwintek.web.backend.api.dto.admin.docRoom.DocRoomRequestDTO;
import com.winwintek.web.backend.api.dto.admin.docRoom.DocRoomResponseDTO;
import com.winwintek.web.backend.api.dto.admin.docRoom.DocRoomSearchDTO;
import com.winwintek.web.backend.api.dto.admin.docRoom.DocRoomSeq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/a/doc-room")
public class DocumentRoomController {

    @Autowired
    private IDocumentRoomService docRoomService;


    @PostMapping ("/")
    public ApiResponse<Page<DocRoomResponseDTO>> getAllDocRooms(@RequestBody DocRoomSearchDTO docRoomSearchDTO) {
        Page<DocRoomResponseDTO> docRooms = docRoomService.getAllDocRooms(docRoomSearchDTO);
        ApiResponse<Page<DocRoomResponseDTO>> apiResponse = new ApiResponse<>(ApiStatus.SUCCESS, docRooms);
        return apiResponse;

    }

    @PostMapping ("/detail")
    public ApiResponse<DocRoomRequestDTO> getDocRoomById(@RequestBody DocRoomSeq docRoomSeq) {
        DocRoomRequestDTO docRoom = docRoomService.detailDocRoom(docRoomSeq);
        ApiResponse<DocRoomRequestDTO> apiResponse = new ApiResponse<>(ApiStatus.SUCCESS, docRoom);
        return apiResponse;
    }

    @PostMapping ("/create")
    public ApiResponse<String> createDocRoom(@RequestBody DocRoomRequestDTO docRoomRequestDTO) {
        String docRoomId = docRoomService.createDocRoom(docRoomRequestDTO);
        ApiResponse<String> apiResponse = new ApiResponse<>(ApiStatus.SUCCESS, docRoomId);
        return apiResponse;
    }

    @PostMapping ("/update")
    public ApiResponse<Boolean> updateDocRoom(@RequestBody DocRoomRequestDTO docRoomRequestDTO) {
        Boolean updSuccess = docRoomService.updDocRoom(docRoomRequestDTO);
        ApiResponse<Boolean> apiResponse = new ApiResponse<>(ApiStatus.SUCCESS, updSuccess);
        return apiResponse;

    }

    @PostMapping ("/delete")
    public ApiResponse<Boolean> deleteDocRoom(@RequestBody List<DocRoomResponseDTO> docRoomResponseDTOS) {
        Boolean updSuccess = docRoomService.deleteDocRoom(docRoomResponseDTOS);
        ApiResponse<Boolean> apiResponse = new ApiResponse<>(ApiStatus.SUCCESS, updSuccess);
        return apiResponse;

    }

}
