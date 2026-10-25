package com.winwintek.web.backend.api.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultDTO<T> {
    private T data;
    private int code = HttpStatus.OK.value();;
    private String message = HttpStatus.OK.getReasonPhrase();;

    public ResultDTO(T data) {
        this.data = data;

        // 정상 실행 시 default value(exception 발생 시 해당 code, message 입력됨)
        code = HttpStatus.OK.value();
        message = HttpStatus.OK.getReasonPhrase();
    }

}
