package com.winwintek.web.backend.api.domain.client.student.schoolNotice.impl;

import com.winwintek.web.backend.api.domain.client.student.schoolNotice.service.ISchoolNoticeService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.client.student.schoolNotice.SchoolNoticeDetailResDTO;
import com.winwintek.web.backend.api.dto.client.student.schoolNotice.SchoolNoticeListResDTO;
import com.winwintek.web.backend.api.dto.client.student.schoolNotice.SchoolNoticeReqDTO;
import com.winwintek.web.backend.api.dto.client.student.schoolNotice.SchoolNoticeResDTO;
import com.winwintek.web.backend.api.entity.client.student.schoolNotice.ISchoolNoticeDetailOutput;
import com.winwintek.web.backend.api.entity.client.student.schoolNotice.ISchoolNoticeListOutput;
import com.winwintek.web.backend.api.entity.client.student.schoolNotice.ISchoolNoticeOutput;
import com.winwintek.web.backend.api.repository.client.student.schoolNotice.SchoolNoticeRepository;
import org.hibernate.type.descriptor.java.ClobJavaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.winwintek.web.backend.api.common.Constants.STATE_N;
import static com.winwintek.web.backend.api.common.Constants.STATE_Y;

@Service
public class SchoolNoticeServiceImpl implements ISchoolNoticeService {

    @Autowired
    private SchoolNoticeRepository noticeRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<SchoolNoticeResDTO> getSchoolNoticeList() {
        List<ISchoolNoticeOutput> list = noticeRepository.getSchoolNoticeList(STATE_Y, STATE_N);
        List<SchoolNoticeResDTO> listResponse = list.stream()
                .map(item -> modelMapper.map(item, SchoolNoticeResDTO.class))
                .collect(Collectors.toList());
        return listResponse;
    }

    @Override
    public Page<SchoolNoticeListResDTO> getSChoolNoticeList(SchoolNoticeReqDTO request) {
        int page = request.getPage();
        int size = request.getSize();
        String sort = request.getSort();

        Pageable pageable = commonService.buildPageable(page, size, sort);
        Page<ISchoolNoticeListOutput> listSchoolNoticeResponse = noticeRepository.getSchoolNoticeList(request,
                pageable, STATE_N, STATE_Y);

        Page<SchoolNoticeListResDTO> pageListResponse = listSchoolNoticeResponse.map(item -> modelMapper.map(item,
                SchoolNoticeListResDTO.class));
        return pageListResponse;

    }

    @Override
    public SchoolNoticeDetailResDTO getSchoolNoticeDetail(String id) {
        ISchoolNoticeDetailOutput detailNotice = noticeRepository.getSchoolNoticeDetail(id, STATE_N);
        ClobJavaType clobJavaType = new ClobJavaType();
        String content = clobJavaType.toString(detailNotice.getContent());
        return new SchoolNoticeDetailResDTO(detailNotice.getTitle(), detailNotice.getRegDate(), content);

    }

}
