package com.alice.hotel.api.entity.system.menuHistory;

import com.alice.hotel.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;

@DynamicUpdate
@Entity
@Table(name="CPS_MENU_CONN_HIS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuHistoryEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String menuConnHisId;
    private String connId;
    private String connUrl;
    private String connIp;
}
