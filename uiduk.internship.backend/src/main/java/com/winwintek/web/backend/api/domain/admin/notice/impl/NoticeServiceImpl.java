package com.winwintek.web.backend.api.domain.admin.notice.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.notice.service.NoticeService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.admin.notice.NoticeDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.notice.NoticeReqDTO;
import com.winwintek.web.backend.api.dto.admin.notice.NoticeResDTO;
import com.winwintek.web.backend.api.dto.admin.notice.NoticeSearchDTO;
import com.winwintek.web.backend.api.entity.admin.notice.INoticeCusDTO;
import com.winwintek.web.backend.api.entity.admin.notice.INoticeDetailCusDTO;
import com.winwintek.web.backend.api.entity.admin.notice.NoticeEntity;
import com.winwintek.web.backend.api.repository.admin.notice.NoticeRepository;
import com.winwintek.web.backend.api.util.Utils;
import jakarta.transaction.Transactional;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_NOTICE;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_NOTICE;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Override
    @Transactional
    public String createNotice(NoticeReqDTO noticeReqDTO) {
        NoticeEntity noticeEntity = modelMapper.map(noticeReqDTO, NoticeEntity.class);
        noticeEntity.setNoticeSeq(sequenceGenerator.getNextSequence(PREFIX_NOTICE.toString(), TABLE_NOTICE.toString()));
        noticeEntity.setDelYn(Constants.STATE_N);
        noticeRepository.save(noticeEntity);
        return noticeEntity.getNoticeSeq();
    }

    @Override
    @Transactional
    public Boolean allRemove(List<String> request) {
        noticeRepository.allRemove(request, Constants.STATE_Y);
        return true;
    }

    @Override
    public Page<NoticeResDTO> getListNotice(NoticeSearchDTO noticeSearchDTO) {
        int page = noticeSearchDTO.getPage();
        int size = noticeSearchDTO.getSize();
        String sort = noticeSearchDTO.getSort();
        Pageable pageable = commonService.buildPageable(page, size, sort);

        Page<INoticeCusDTO> noticeCusDTOS = noticeRepository.getNotice(noticeSearchDTO, pageable, Constants.STATE_N);

        Page<NoticeResDTO> noticeResDTOS = noticeCusDTOS.map(item -> modelMapper.map(item, NoticeResDTO.class));
        return noticeResDTOS;
    }

    @Override
    public NoticeDetailResDTO detailNotice(String id) {
        INoticeDetailCusDTO iNoticeDetailCusDTO = noticeRepository.findNoticeSeqAndDelYn(id, Constants.STATE_N);
        NoticeDetailResDTO noticeDetailResDTO = modelMapper.map(iNoticeDetailCusDTO, NoticeDetailResDTO.class);
        ClobJavaType clobJavaType = new ClobJavaType();
        noticeDetailResDTO.setContent(iNoticeDetailCusDTO.getContent() != null ? clobJavaType.toString(
                iNoticeDetailCusDTO.getContent()) : null);
        return noticeDetailResDTO;
    }

    @Override
    @Transactional
    public String updateNotice(NoticeReqDTO noticeReqDTO) {
        NoticeEntity noticeEntity = noticeRepository.findByNoticeSeqAndDelYn(noticeReqDTO.getNoticeSeq(),
                Constants.STATE_N);
        noticeEntity.setTitle(noticeReqDTO.getTitle());
        noticeEntity.setContent(noticeReqDTO.getContent());
        noticeEntity.setUseYn(noticeReqDTO.getUseYn());
        noticeEntity.setTopFixYn(noticeReqDTO.getTopFixYn());
        noticeEntity.setStartDateShow(Utils.formatDate(noticeReqDTO.getStartDateShow()));
        noticeEntity.setEndDateShow(Utils.formatDate(noticeReqDTO.getEndDateShow()));
        noticeRepository.save(noticeEntity);
        return noticeEntity.getNoticeSeq();
    }

}
