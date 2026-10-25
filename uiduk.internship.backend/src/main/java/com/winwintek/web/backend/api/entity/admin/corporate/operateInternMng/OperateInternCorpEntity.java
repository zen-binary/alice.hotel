package com.winwintek.web.backend.api.entity.admin.corporate.operateInternMng;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
@Table(name = "INTERN_PLAN")
public class OperateInternCorpEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String internPlanSeq;

    private String internPlanNm;

    private Integer internRecruitmentQuantity;

    private Date internshipStr;

    private Date internshipEnd;

    private String status;

}
