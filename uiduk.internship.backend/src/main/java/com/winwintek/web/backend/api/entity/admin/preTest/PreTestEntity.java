package com.winwintek.web.backend.api.entity.admin.preTest;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table (name = "CORPORATE_PRE_TEST")
@Data
public class PreTestEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String corporatePreTestSeq;

    private String standardIndustryType;

    private String internPlanSeq;

    private String delYn;

}

