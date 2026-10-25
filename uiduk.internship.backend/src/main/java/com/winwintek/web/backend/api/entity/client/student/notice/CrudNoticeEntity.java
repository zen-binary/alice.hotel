package com.winwintek.web.backend.api.entity.client.student.notice;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;


@Entity
@Table (name="NOTICE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CrudNoticeEntity extends AbstractAuditingEntity<String> implements Serializable {

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
