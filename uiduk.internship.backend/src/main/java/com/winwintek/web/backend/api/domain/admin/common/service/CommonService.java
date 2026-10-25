package com.winwintek.web.backend.api.domain.admin.common.service;

import com.winwintek.web.backend.api.common.GlobalEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class CommonService {

    public Long getUserTsid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getName().equals(String.valueOf(GlobalEnum.AnomyousUser_String))){
            return GlobalEnum.AnomyousUser_Long.getValue();
        }
        return Long.parseLong(authentication.getName());
    }

    public Long getCurrentTime(){
        long unixTime = System.currentTimeMillis() / 1000L;
        return unixTime;
    }

    public static String convertUnixTimeToDateTime(long unixTime) {
        Instant instant = Instant.ofEpochSecond(unixTime);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter);
    }
}
