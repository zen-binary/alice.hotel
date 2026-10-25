package com.winwintek.web.backend.api.entity.client.student.qna;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table (name="QNA")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CrudQNAEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String qnaSeq;
    private String userId;
    private String title;
    private String content;
    private String answer;
    private String delYn;
}
