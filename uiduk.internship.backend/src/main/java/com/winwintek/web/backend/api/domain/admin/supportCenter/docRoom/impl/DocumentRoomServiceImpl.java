package com.winwintek.web.backend.api.domain.admin.supportCenter.docRoom.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.common.GlobalEnum;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.supportCenter.docRoom.service.IDocumentRoomService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.admin.docRoom.DocRoomRequestDTO;
import com.winwintek.web.backend.api.dto.admin.docRoom.DocRoomResponseDTO;
import com.winwintek.web.backend.api.dto.admin.docRoom.DocRoomSearchDTO;
import com.winwintek.web.backend.api.dto.admin.docRoom.DocRoomSeq;
import com.winwintek.web.backend.api.entity.admin.supportCenter.docRoom.DocumentRoomSupportCenterEntity;
import com.winwintek.web.backend.api.entity.admin.supportCenter.docRoom.IDocRoomDetailCusDTO;
import com.winwintek.web.backend.api.entity.admin.supportCenter.docRoom.IDocRoomSupportCenter;
import com.winwintek.web.backend.api.repository.admin.docRoom.DocumentRoomRepository;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentRoomServiceImpl implements IDocumentRoomService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DocumentRoomRepository documentRoomRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private SequenceGenerator sequenceGenerator;


    @Override
    public Page<DocRoomResponseDTO> getAllDocRooms(DocRoomSearchDTO docRoomSearchDTO) {
        int page = docRoomSearchDTO.getPage();
        int size = docRoomSearchDTO.getSize();
        String sort = docRoomSearchDTO.getSort();

        Pageable pageable = commonService.buildPageable(page, size, sort);

        Page<IDocRoomSupportCenter> docRooms = documentRoomRepository.getDocRoomList(docRoomSearchDTO,
                String.valueOf(GlobalEnum.STATE_N), pageable);

        Page<DocRoomResponseDTO> docRoomResponseDTOS = docRooms.map(entity -> {
            DocRoomResponseDTO dto = new DocRoomResponseDTO();
            modelMapper.map(entity, dto);
            return dto;
        });
        return docRoomResponseDTOS;
    }


    @Override
    @Transactional
    public String createDocRoom(DocRoomRequestDTO docRoomRequestDTO) {
        DocumentRoomSupportCenterEntity entity = modelMapper.map(docRoomRequestDTO, DocumentRoomSupportCenterEntity.class);
        entity.setDocSeq(sequenceGenerator.getNextSequence(String.valueOf(GlobalEnum.PREFIX_DOC_ROOM),
                String.valueOf(GlobalEnum.TABLE_DOC_ROOM)));
        entity.setDelYn(String.valueOf(GlobalEnum.STATE_N));
        entity.setUseYn(docRoomRequestDTO.getUseYn());

        entity = documentRoomRepository.save(entity);
        DocRoomRequestDTO dto = modelMapper.map(entity, DocRoomRequestDTO.class);

        return dto.getDocSeq();
    }

    @Override
    public DocRoomRequestDTO detailDocRoom(DocRoomSeq docSeq) {
        IDocRoomDetailCusDTO docRoom = documentRoomRepository.findDocRoomById(docSeq.getDocSeq(), Constants.STATE_N);
        DocRoomRequestDTO responseDTO = modelMapper.map(docRoom, DocRoomRequestDTO.class);

        ClobJavaType clobJavaType = new ClobJavaType();
        responseDTO.setContent(docRoom.getContent() != null ? clobJavaType.toString(docRoom.getContent()) : null);
        return responseDTO;
    }

    @Override
    @Transactional
    public Boolean updDocRoom(DocRoomRequestDTO docRoomRequestDTO) {
        DocumentRoomSupportCenterEntity entity = documentRoomRepository.findByDocSeqAndDelYn(docRoomRequestDTO.getDocSeq(),
                Constants.STATE_N);
        entity.setContent(docRoomRequestDTO.getContent());
        entity.setTopFixYn(docRoomRequestDTO.getTopFixYn());
        entity.setUseYn(docRoomRequestDTO.getUseYn());
        entity.setTitle(docRoomRequestDTO.getTitle());
        documentRoomRepository.save(entity);
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteDocRoom(List<DocRoomResponseDTO> docRoomResponseDTOS) {

        List<String> docSeqList = docRoomResponseDTOS.stream().map(DocRoomResponseDTO::getDocSeq).toList();

        documentRoomRepository.updateDelYnForMultipleDocs(docSeqList, GlobalEnum.STATE_Y.toString());
        return true;
    }

}
