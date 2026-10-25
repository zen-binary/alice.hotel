package com.winwintek.web.backend.api.entity.system;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@Table (name = "CPS_USER")
@Getter
@Setter
public class VuserOutEntity {

    @Id
    private String userId;

    private String userPw;

    private String userNo;

    private String userName;

}
