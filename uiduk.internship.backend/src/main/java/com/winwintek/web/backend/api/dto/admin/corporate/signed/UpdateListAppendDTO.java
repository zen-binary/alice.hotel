package com.winwintek.web.backend.api.dto.admin.corporate.signed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateListAppendDTO {
    private List<String> lstId;
}
