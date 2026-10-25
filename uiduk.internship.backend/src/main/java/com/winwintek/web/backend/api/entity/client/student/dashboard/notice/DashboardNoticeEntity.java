package com.winwintek.web.backend.api.entity.client.student.dashboard.notice;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@Table (name = "NOTICE")
@Data
public class DashboardNoticeEntity {

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
