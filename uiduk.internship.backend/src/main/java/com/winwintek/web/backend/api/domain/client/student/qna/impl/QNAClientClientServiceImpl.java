package com.winwintek.web.backend.api.domain.client.student.qna.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.client.student.qna.service.IQNAClientService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.client.student.qna.*;
import com.winwintek.web.backend.api.entity.client.student.qna.CrudQNAEntity;
import com.winwintek.web.backend.api.entity.client.student.qna.IQNADetailOutput;
import com.winwintek.web.backend.api.entity.client.student.qna.IQNAOutput;
import com.winwintek.web.backend.api.repository.client.student.qna.QNARepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.winwintek.web.backend.api.common.Constants.STATE_N;
import static com.winwintek.web.backend.api.common.Constants.STATE_Y;
import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_QNA;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_QNA;

@Service
public class QNAClientClientServiceImpl implements IQNAClientService {

    @Autowired
    private QNARepository qnaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Override
    public Page<QNAResDTO> getQNAList(QNASearchReqDTO req) {

        String regId = commonService.getFullUserInfo().getUserId();

        int page = req.getPage();
        int size = req.getSize();
        String sort = req.getSort();

        Pageable pageable = commonService.buildPageable(page, size, sort);

        Page<IQNAOutput> qnaList = qnaRepository.getQnaList(req, regId, pageable, STATE_N);
        return qnaList.map(item -> modelMapper.map(item, QNAResDTO.class));
    }

    @Override
    public QNADetailResDTO getQNADetail(String id) {
        IQNADetailOutput detailOutput = qnaRepository.getQNADetail(id, STATE_N);
        return modelMapper.map(detailOutput, QNADetailResDTO.class);
    }

    @Override
    @Transactional
    public QNARegResDTO createQNA(QNARegReqDTO req) {

        CrudQNAEntity qnaEntity = new CrudQNAEntity();

        qnaEntity.setQnaSeq(sequenceGenerator.getNextSequence(PREFIX_QNA.toString(), TABLE_QNA.toString()));
        qnaEntity.setUserId(commonService.getFullUserInfo().getUserId());
        qnaEntity.setTitle(req.getTitle());
        qnaEntity.setContent(req.getContent());
        qnaEntity.setDelYn(STATE_N);

        qnaRepository.save(qnaEntity);

        return modelMapper.map(qnaEntity, QNARegResDTO.class);
    }

    @Override
    @Transactional
    public boolean deleteQNA(String id) {
        CrudQNAEntity currentQNA = qnaRepository.findByQnaSeq(id)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        currentQNA.setDelYn(STATE_Y);
        qnaRepository.save(currentQNA);
        return true;
    }


}
