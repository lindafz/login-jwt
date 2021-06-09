package com.lindazf.login.jwt.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {


//    @ApiModelProperty(notes = "The user name. Max characters = 255", position = 1, example = "skyadmin")
//    private String operatorName;

    @ApiModelProperty(notes = "The JWT token.", position = 2, example = "ABC.DE.FG")
    private String authCode;

    @ApiModelProperty(notes = "The JWT token valid time in minutes", position = 3, example = "10")
    private Integer validMinutes;

    protected boolean authorized;

}
