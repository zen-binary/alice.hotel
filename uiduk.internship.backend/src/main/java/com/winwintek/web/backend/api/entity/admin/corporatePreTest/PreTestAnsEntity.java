package com.winwintek.web.backend.api.entity.admin.corporatePreTest;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table(name="PRE_TEST_ANS")
@Data
public class PreTestAnsEntity  extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String preTestAnsSeq;

    private String corporatePreTestSeq;

    private String preTestDetailSeq;

    private String addContent;

    private String delYn;

}
