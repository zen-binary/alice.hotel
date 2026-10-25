package com.winwintek.web.backend.api.entity.admin.supportCenter.weekReport;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
@Table (name = "STD_WEEK_REPORT")
public class WeekReportSupportCenterEntity extends AbstractAuditingEntity<String> implements Serializable {

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
