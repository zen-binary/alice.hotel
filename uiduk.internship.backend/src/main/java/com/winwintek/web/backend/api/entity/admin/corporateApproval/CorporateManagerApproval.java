package com.winwintek.web.backend.api.entity.admin.corporateApproval;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;


@DynamicUpdate
@Entity
@Table (name = "CORPORATE_MEMBERS_MANAGER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CorporateManagerApproval extends AbstractAuditingEntity<String> implements Serializable {
    @Id
    private String commSeq;

    private String cormSeq;

    private String commNm;

    private String commDept;

    private String positionName;

    private String companyPhone;

    private String mobilePhone;

    private String email;

    private String useYn;

    private String delYn;
}
