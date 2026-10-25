package com.winwintek.web.backend.api.entity.client.student.reportWeek;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table (name = "STD_ATTENDANCE")
@Data
public class WeekReportStdAttendanceEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String stdAttendanceSeq;

    private String internStdSeq;

    private String internDetailSeq;

    private Date attendanceDate;

    private String status;

    private String delYn;

}
