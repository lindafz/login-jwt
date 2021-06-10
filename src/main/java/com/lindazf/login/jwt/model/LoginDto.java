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
    private String userName;

    @NotNull
    @ApiModelProperty(notes = "The password is hashed. Max characters = 255", position = 2, example = "A5678!CD")
    private String password;

}
