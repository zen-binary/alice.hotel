package com.winwintek.web.backend.api.entity.admin.staffIntern;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table (name = "V_STD_CDP_STAF")
public class StaffInternEntity implements Serializable {

    @Id
    private String staffNo;

    private String staffFgCd;

    private String staffFg;

    private String wkgdCd;

    private String wkgdNm;

    private String deptNm;

    private String korNm;

    private String dengCd;

    private String officeCall;

    private String phone;

    private String email;

    private String useFg;

    private String genFg;

    private String genFgNm;

    private String career;

    private String campusCd;

    private String campusCdNm;

    private String staffNoTmp;

    private String pw;

    private String subjectCd;

    private String subjectNm;

    private String jaejikGbn;

    private String jaejikGbnNm;

}
