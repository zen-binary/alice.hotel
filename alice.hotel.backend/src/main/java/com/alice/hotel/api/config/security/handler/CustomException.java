package com.alice.hotel.api.config.security.handler;

import com.alice.hotel.api.common.ApiStatus;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ApiStatus apiStatus;

    public CustomException(ApiStatus apiStatus) {
        super(apiStatus.getMessage());
        this.apiStatus = apiStatus;
    }

}
