package com.lindazf.login.jwt.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDto {
	 private Long userId;
	 
	 @ApiModelProperty(notes = "The user name. Max characters = 255", position = 2, example = "Alex")
	 private String userName;
	 
	 @ApiModelProperty(notes = "The password. Max characters = 255", position = 3, example = "A5678")
	 private String password;
	 
	 @ApiModelProperty(notes = "The user full name", position = 4, example = "Alex Wilson")
	 private String fullName;
	 
	 @ApiModelProperty(notes = "The role name. Max characters = 255", position = 5, example = "ROLE_ADMIN")
	 private String roleName;

}
