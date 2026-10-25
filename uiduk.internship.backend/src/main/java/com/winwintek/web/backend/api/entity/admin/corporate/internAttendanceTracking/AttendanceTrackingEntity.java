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
@Table (name = "STD_ATTENDANCE")
public class AttendanceTrackingEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String stdAttendanceSeq;

    private String internStdSeq;

    private String internDetailSeq;

    private Date attendanceDate;

    private String status;

    private String delYn;

}
