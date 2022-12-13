package com.example.projectbluehair.common.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private final HttpStatus httpStatus;
    @JsonProperty(value = "msg")
    private final String message;
    private final Object data;

    @Builder
    public ResponseDto(HttpStatus httpStatus, String message, Object data){
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }
}
