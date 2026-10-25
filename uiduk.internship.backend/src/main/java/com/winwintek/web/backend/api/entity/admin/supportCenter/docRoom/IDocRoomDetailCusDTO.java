package com.winwintek.web.backend.api.entity.admin.supportCenter.docRoom;

import java.sql.Clob;

public interface IDocRoomDetailCusDTO {

    String getDocSeq();

    String getTitle();

    String getUseYn();

    Clob getContent();

    String getTopFixYn();

}
