package com.winwintek.web.backend.api.entity.admin.internPlanDetail;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table (name = "INTERN_PLAN_DETAIL")
public class InternPlanDetailEntity extends AbstractAuditingEntity<Long> implements Serializable {
    /**
     * PK
     */
    @Id
//    @Column (name = "INTERN_DETAIL_SEQ")
    private String internDetailSeq;

    /**
     * INTERN_PLAN 테이블과 연동 키
     */
//    @Column (name = "INTERN_PLAN_SEQ")
    private String internPlanSeq;

    /**
     * 운영계획 주차
     */
//    @Column (name = "WEEK")
    private String week;

    /**
     * 시작일
     */
//    @Column (name = "START_DATE")
    private Date startDate;

    /**
     * 종료일
     */
//    @Column (name = "END_DATE")
    private Date endDate;
    /** 운영계획 내용 */
//    @Column (name = "CONTENT")
    @Lob
    private String content;

    /**
     * 삭제여부
     */
//    @Column (name = "DEL_YN")
    private String delYn;

}


