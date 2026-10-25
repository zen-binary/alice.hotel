package com.winwintek.web.backend.api.domain.common.impl;

import com.winwintek.web.backend.api.domain.common.service.CodeMngService;
import com.winwintek.web.backend.api.dto.common.codeMng.CodeMngDTO;
import com.winwintek.web.backend.api.dto.common.codeMng.CodeMngResDTO;
import com.winwintek.web.backend.api.dto.common.codeMng.CodeMngResListDTO;
import com.winwintek.web.backend.api.entity.common.codeMng.CodeMngEntity;
import com.winwintek.web.backend.api.repository.common.codeMng.CodeMngRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CodeMngServiceImpl implements CodeMngService {

    @Autowired
    private CodeMngRepository codeMngRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CodeMngDTO> getByUpId(CodeMngResDTO codeMngResDTO) {
        List<CodeMngEntity> listResult = codeMngRepository.selectByUpCdId(codeMngResDTO.getUpCdId());

        List<CodeMngDTO> listCode = listResult.stream().map(item -> modelMapper.map(item, CodeMngDTO.class))
                .collect(Collectors.toList());

        return listCode;
    }

    @Override
    public List<CodeMngDTO> getListCode(CodeMngResListDTO codeMngResDTO) {
        List<CodeMngEntity> listResult = codeMngRepository.findByUpCdIdIn(codeMngResDTO.getUpCdIdList());

        List<CodeMngDTO> listCode = listResult.stream().map(item -> modelMapper.map(item, CodeMngDTO.class))
                .collect(Collectors.toList());

        return listCode;
    }
}
