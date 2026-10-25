package com.winwintek.web.backend.api.entity.admin.qna;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table (name = "QNA")
@Data
public class QnaEntity extends AbstractAuditingEntity implements Serializable {

    @Id
    private String qnaSeq;

    private String userId;

    private String title;

    private String content;

    private String answer;

    private String delYn;

}
