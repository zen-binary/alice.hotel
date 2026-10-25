package com.winwintek.web.backend.api.entity.client.student.reportWeek;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;

@DynamicUpdate
@Entity
@Table (name = "STD_WEEK_REPORT")
@Data
public class ReportWeekStudentEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String stdWeekReportSeq;

    private String internDetailSeq;

    private String internStdSeq;

    private Date dateWeek;

    private String inHour;

    private String inMinute;

    private String outHour;

    private String outMinute;

    private String jobContent;

    private String acquiredKnowledge;

    private String delYn;

}
