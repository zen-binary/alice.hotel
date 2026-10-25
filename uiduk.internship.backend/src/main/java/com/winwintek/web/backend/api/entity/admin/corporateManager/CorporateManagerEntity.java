package com.winwintek.web.backend.api.entity.admin.corporateManager;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "CORPORATE_MEMBERS_MANAGER")
public class CorporateManagerEntity extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
//    @Column (name = "COMM_SEQ")
    private String commSeq;

    /**
     * CORPORATE_MEMBERS 테이블과 연동 키
     */
//    @Column (name = "CORM_SEQ")
    private String cormSeq;

    /**
     * 딤당자 성명
     */
//    @Column (name = "COMM_NM")
    private String commNm;

    /**
     * 담당자 부서
     */
//    @Column (name = "COMM_DEPT")
    private String commDept;

    /**
     * 담당자 직위
     */
//    @Column (name = "POSITION_NAME")
    private String positionName;

    /**
     * 담당자 회사 전화번호
     */
//    @Column (name = "COMPANY_PHONE")
    private String companyPhone;

    /**
     * 담당자 핸드폰번호
     */
//    @Column (name = "MOBILE_PHONE")
    private String mobilePhone;

    /**
     * 담당자 이메일
     */
//    @Column (name = "EMAIL")
    private String email;

    /**
     * 사용 여부
     */
//    @Column (name = "USE_YN")
    private String useYn;

    /**
     * 삭제 여부
     */
//    @Column (name = "DEL_YN")
    private String delYn;

}
