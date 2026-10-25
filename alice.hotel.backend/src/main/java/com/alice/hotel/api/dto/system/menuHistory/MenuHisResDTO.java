package com.alice.hotel.api.dto.system.menuHistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuHisResDTO {

    private String rowNumber;

    private String url;

    private String userId;

    private String name;

    private String connDate;

}
