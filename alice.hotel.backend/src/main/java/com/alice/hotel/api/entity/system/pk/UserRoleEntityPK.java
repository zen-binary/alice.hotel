package com.alice.hotel.api.entity.system.pk;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntityPK implements Serializable {
    @Column(name = "user_tsid")
    private String userTsid;

    @Column(name = "rl_id")
    private String rlId;
}
