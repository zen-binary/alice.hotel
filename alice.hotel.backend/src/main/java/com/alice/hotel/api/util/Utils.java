package com.alice.hotel.api.util;

import com.alice.hotel.api.common.ApiStatus;
import com.alice.hotel.api.config.security.handler.CustomException;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Utils {

    public static String camelToSnake(String camelCase) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        String snakeCase = camelCase.replaceAll(regex, replacement).toLowerCase();
        return snakeCase;
    }

    public static Date formatDate(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            throw new CustomException(ApiStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static String formatDateToString(Date value) {
        if (Objects.nonNull(value)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return dateFormat.format(value);
            } catch (Exception e) {
                throw new CustomException(ApiStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return "";
    }

}
