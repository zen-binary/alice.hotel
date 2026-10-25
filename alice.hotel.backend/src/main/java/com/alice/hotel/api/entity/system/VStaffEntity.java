package com.alice.hotel.api.entity.system;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@Table (name = "V_STD_CDP_STAF")
@Getter
@Setter
public class VStaffEntity {

    @Id
    private String staffNo;

    private String pw;

    private String staffFg;

    private String korNm;

    private String deptNm;

    private String deptCd;

}
