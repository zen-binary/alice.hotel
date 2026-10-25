package com.winwintek.web.backend.api.entity.client.corporateRegister;

import com.winwintek.web.backend.api.common.Constants;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@Table(name = "SYS_USER_ROLE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CorporateUserRoleRegister{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long urId;

    private Long rlId;

    private String userId;

    private String publicInfo = Constants.STATE_N;


}
