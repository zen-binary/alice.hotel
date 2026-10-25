package com.alice.hotel.api.entity.common.codeMng;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table(name="CMMN_CODE_MNG")
@Data
public class CodeMngEntity implements Serializable {
    @Id
    private String cdId;

    private String upCdId;

    private String cdCategory;

    private String cdNm;

    private Long ordNo;
}
