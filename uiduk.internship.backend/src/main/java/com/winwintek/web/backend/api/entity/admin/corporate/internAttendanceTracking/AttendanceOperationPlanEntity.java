package com.winwintek.web.backend.api.entity.admin.corporate.internAttendanceTracking;

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
@Table (name = "INTERN_PLAN_DETAIL")
public class AttendanceOperationPlanEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String internDetailSeq;

    private String internPlanSeq;

    private String week;

    private Date startDate;

    private Date endDate;

    private String delYn;

}
