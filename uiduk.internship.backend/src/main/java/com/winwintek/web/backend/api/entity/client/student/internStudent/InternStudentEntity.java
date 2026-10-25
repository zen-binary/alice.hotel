package com.winwintek.web.backend.api.entity.client.student.internStudent;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="INTERN_STD")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InternStudentEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String internStdSeq;

    private String stdNo;

    private String internPlanSeq;

    private String approveStatus;

    private String approveDate;

    private String internshipStatus;

    private String delYn;

}
