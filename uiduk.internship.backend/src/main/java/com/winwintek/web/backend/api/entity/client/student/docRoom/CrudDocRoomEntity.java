package com.winwintek.web.backend.api.entity.client.student.docRoom;

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
@Table (name="DOC_ROOM")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CrudDocRoomEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String docSeq;
    private String title;
    private String content;
    private String useYn;
    private String topFixYn;
    private String delYn;

}
