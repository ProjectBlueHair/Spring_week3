package com.example.projectbluehair.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SuccessResponse {

    private ResponseDto buildResponseDto(HttpStatus httpStatus, String message, Object data){
        return ResponseDto.builder()
                .httpStatus(httpStatus)
                .message(message)
                .data(data)
                .build();
    }

    //header 상태코드 : httpStatus / body "httpStatus": httpStatus, "msg": message, "data": data
    public ResponseEntity<ResponseDto> respond(HttpStatus httpStatus, String message, Object data){
        ResponseDto responseBody = buildResponseDto(httpStatus, message, data);
        return new ResponseEntity<>(responseBody, httpStatus);
    }


    //header 상태코드 : httpStatus / body "data": data
    public ResponseEntity<ResponseDto> respondDataOnly(HttpStatus httpStatus, Object data){
        ResponseDto responseBody = buildResponseDto(null, null, data);
        return new ResponseEntity<>(responseBody, httpStatus);
    }
}
