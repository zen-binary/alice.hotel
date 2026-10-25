package com.winwintek.web.backend.api.entity.client.student.docRoom;

import java.sql.Clob;

public interface IDocRoomDetailOutput {
    String getTitle();
    String getRegDate();
    Clob getContent();
}
