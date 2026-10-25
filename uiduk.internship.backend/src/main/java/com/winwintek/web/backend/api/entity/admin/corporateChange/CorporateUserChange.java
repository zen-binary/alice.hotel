package com.winwintek.web.backend.api.entity.admin.corporateChange;

import com.winwintek.web.backend.api.common.Constants;
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
@Table (name = "CPS_USER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CorporateUserChange extends AbstractAuditingEntity<String> implements Serializable {
    @Id
    private String userId;

    private String userPw;

    private String userName;

    private String division;

    private String userDiv;

    private String useYn = Constants.STATE_Y;

    private String delYn = Constants.STATE_N;
}
