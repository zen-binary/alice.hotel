package com.winwintek.web.backend.api.domain.client.student.docRoom.impl;

import com.winwintek.web.backend.api.domain.client.student.docRoom.service.IDocRoomService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.client.student.docRoom.DocRoomDetailResDTO;
import com.winwintek.web.backend.api.dto.client.student.docRoom.DocRoomReqDTO;
import com.winwintek.web.backend.api.dto.client.student.docRoom.DocRoomResDTO;
import com.winwintek.web.backend.api.entity.client.student.docRoom.IDocRoomDetailOutput;
import com.winwintek.web.backend.api.entity.client.student.docRoom.IDocRoomOutput;
import com.winwintek.web.backend.api.repository.client.student.docRoom.DocRoomRepository;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.winwintek.web.backend.api.common.Constants.STATE_N;
import static com.winwintek.web.backend.api.common.Constants.STATE_Y;

@Service
public class DocRoomServiceImpl implements IDocRoomService {

    @Autowired
    private DocRoomRepository docRoomRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Page<DocRoomResDTO> getSChoolNoticeList(DocRoomReqDTO request) {

        int page = request.getPage();
        int size = request.getSize();
        String sort = request.getSort();

        Pageable pageable = commonService.buildPageable(page, size, sort);

        Page<IDocRoomOutput> listSchoolNoticeResponse = docRoomRepository.getSchoolNoticeList(request,
                pageable, STATE_N, STATE_Y);

        Page<DocRoomResDTO> pageListResponse = listSchoolNoticeResponse.map(item -> modelMapper.map(item,
                DocRoomResDTO.class));

        return pageListResponse;
    }

    @Override
    public DocRoomDetailResDTO getSchoolNoticeDetail(String id) {

        IDocRoomDetailOutput detailNotice = docRoomRepository.getSchoolNoticeDetail(id, STATE_N);
        ClobJavaType clobJavaType = new ClobJavaType();
        String content = clobJavaType.toString(detailNotice.getContent());
        return new DocRoomDetailResDTO(detailNotice.getTitle(), detailNotice.getRegDate(), content);

    }

}
