package com.winwintek.web.backend.api.entity.common.stdIntern;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "INTERN_STD")
@Getter
@Setter
public class StudentInternEntity {

    @Id
    private String internStdSeq;

    private String stdNo;

    private String internPlanSeq;

    private String delYn;

}
