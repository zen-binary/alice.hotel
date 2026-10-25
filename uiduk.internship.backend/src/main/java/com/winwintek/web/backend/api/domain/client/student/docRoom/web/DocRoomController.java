package com.winwintek.web.backend.api.domain.client.student.docRoom.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.student.docRoom.service.IDocRoomService;
import com.winwintek.web.backend.api.dto.client.student.docRoom.DocRoomDetailResDTO;
import com.winwintek.web.backend.api.dto.client.student.docRoom.DocRoomReqDTO;
import com.winwintek.web.backend.api.dto.client.student.docRoom.DocRoomResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("c/doc-room")
public class DocRoomController {

    @Autowired
    private IDocRoomService docRoomService;

    @PostMapping("/")
    public ApiResponse<Page<DocRoomResDTO>> getSchoolNoticeList(@RequestBody DocRoomReqDTO request){
        Page<DocRoomResDTO> response = docRoomService.getSChoolNoticeList(request);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }

    @PostMapping ("/detail")
    public ApiResponse<DocRoomDetailResDTO> getSchoolNoticeDetail(@RequestBody String id){
        DocRoomDetailResDTO response = docRoomService.getSchoolNoticeDetail(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }

}
