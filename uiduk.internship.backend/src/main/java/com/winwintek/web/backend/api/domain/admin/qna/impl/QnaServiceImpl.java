package com.winwintek.web.backend.api.domain.admin.qna.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.qna.service.QnaService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.admin.qna.QnaDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.qna.QnaReqDTO;
import com.winwintek.web.backend.api.dto.admin.qna.QnaResDTO;
import com.winwintek.web.backend.api.dto.admin.qna.QnaSearchDTO;
import com.winwintek.web.backend.api.entity.admin.qna.IQnaCusDTO;
import com.winwintek.web.backend.api.entity.admin.qna.QnaEntity;
import com.winwintek.web.backend.api.repository.admin.qna.QnaRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QnaServiceImpl implements QnaService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private SequenceGenerator sequenceGenerator;
    @Override
    @Transactional
    public Page<QnaResDTO> getListQna(QnaSearchDTO qnaSearchDTO){
        int page = qnaSearchDTO.getPage();
        int size = qnaSearchDTO.getSize();
        String sort = qnaSearchDTO.getSort();
        Pageable pageable = commonService.buildPageable(page, size, sort);

        Page<IQnaCusDTO> qnaCusDTOS = qnaRepository.getQna(qnaSearchDTO, pageable, Constants.STATE_N);

        Page<QnaResDTO> qnaResDTOS = qnaCusDTOS.map(item -> modelMapper.map(item, QnaResDTO.class));
        return qnaResDTOS;
    }

    @Override
    @Transactional
    public Boolean allRemove(List<String> request) {
        qnaRepository.allRemove(request, Constants.STATE_Y);
        return true;
    }

    @Override
    @Transactional
    public QnaDetailResDTO detailQna(String id) {
        IQnaCusDTO iQnaCusDTO = qnaRepository.getQnaDetail(id, Constants.STATE_N);
        QnaDetailResDTO qnaDetailResDTO = modelMapper.map(iQnaCusDTO, QnaDetailResDTO.class);
        return qnaDetailResDTO;
    }

    @Override
    @Transactional
    public Boolean updateQna(QnaReqDTO qnaReqDTO){
        QnaEntity qnaEntity = qnaRepository.findByQnaSeqAndDelYn(qnaReqDTO.getQnaSeq(), Constants.STATE_N);
        qnaEntity.setAnswer(qnaReqDTO.getAnswer());
        qnaRepository.save(qnaEntity);
        return true;
    }
}
