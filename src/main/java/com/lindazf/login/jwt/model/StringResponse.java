package com.lindazf.login.jwt.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StringResponse {
    @JsonProperty("response")
    private String resposne;

    public StringResponse(String resposne) {
        super();
        this.resposne = resposne;
    }
}
