package com.winwintek.web.backend.api.entity.client.student.dashboard.qna;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@Table (name = "QNA")
@Data
public class DashboardQNAEntity {

    @Id
    private String qnaSeq;
    private String userId;
    private String title;
    private String content;
    private String answer;
    private String delYn;
}
