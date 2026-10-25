package com.winwintek.web.backend.api.domain.admin.corporate.operateInternMng.service;

import com.winwintek.web.backend.api.dto.admin.corporate.operateInternMng.OperateInternCorpResDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.operateInternMng.OperateInternCorpSearchDTO;
import org.springframework.data.domain.Page;

public interface OperateInternCorpService {

    Page<OperateInternCorpResDTO> findListOperateInternCorp(OperateInternCorpSearchDTO searchDTO);

}
