package com.lindazf.login.jwt.model;


import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull
    @ApiModelProperty(notes = "The user name. Max characters = 255", position = 1, example = "Alex")
    //convert to loginId
    private String userName;

    @NotNull
    @ApiModelProperty(notes = "The password is hashed. Max characters = 255", position = 2, example = "A5678!CD")
    private String password;

    @ApiModelProperty(notes = "true or false", position = 3, example = "true")
    private boolean newPassword;

    @ApiModelProperty(notes = "Time-based One-time Password", position = 4, example = "1234")
    private String passCode;
}
