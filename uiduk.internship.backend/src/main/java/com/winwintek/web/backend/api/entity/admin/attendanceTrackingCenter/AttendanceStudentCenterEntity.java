package com.winwintek.web.backend.api.entity.admin.attendanceTrackingCenter;

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
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table (name = "INTERN_STD")
public class AttendanceStudentCenterEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String internStdSeq;

    private String stdNo;

    private String internPlanSeq;

    private String approveStatus;

    private Date approveDate;

    private String internshipStatus;

    private String delYn;
}
