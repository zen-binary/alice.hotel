package com.winwintek.web.backend.api.entity.client.student.schoolNotice;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table (name="NOTICE")
@Data
public class SchoolNoticeEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String noticeSeq;
    private String title;
    private String content;
    private String startDateShow;
    private String endDateShow;
    private String useYn;
    private String topFixYn;
    private String delYn;

}
