package com.winwintek.web.backend.api.dto.admin.supportCenter.completeManage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompleteManageDTO {

    private Long rowNumber;

    private String internPlanNm;

    private String internshipStatus;

    private String colgCdNm;

    private String sustCdNm;

    private String shyrCdNm;

    private String stdNo;

    private String korNm;

    private String handNo;

    private String email;

    private String approveDate;

    private String interStdSeq;

}
