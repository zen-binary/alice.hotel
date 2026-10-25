package com.winwintek.web.backend.api.entity.admin.supportCenter.completeManage;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table (name = "V_STD_CDP_SREG")
@Data
public class VsdSregEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String stdNo;

    private String korNm;

    private String engNm;

    private String campusCd;

    private String campusCdNm;

    private String colgCd;

    private String colgCdNm;

    private String sustCd;

    private String sustCdNm;

    private String mjCd;

    private String mjCdNm;

    private String shyrCd;

    private String shyrCdNm;

    private String shregSt;

    private String shregStNm;

    private String genFg;

    private String genFgNm;

    private String birthDt;

    private String email;

    private String handNo;

    private String guidStaffNm;

    private String guidStaffNo;

    private String entrDt;

    private String grdtDt;

    private String term;

    private String telNo;

    private String fullAddress;

    private String gradtYn;

    private String gradtYear;

    private String credit;

    private String shregBan;

    private String vtTime;

    private String foreignerYn;

    private String mltiMjNm_1;

    private String mltiMjNm_2;

    private String mltiMjNm_3;

    private String mimjDeptNm_1;

    private String mimjDeptNm_2;

    private String mimjDeptNm_3;

    private String handicapYn;

    private String totTakeShtm;

    private String spkiCd;

    private String spkiNm;

    private String pw;

    private String gbn;

    private String gbnNm;

    private String subjectCd;

    private String subjectNm;

    private String hakYy;

    private String jaejikGbnNm;

    private String jaejikGbn;

}
