package com.lindazf.login.jwt.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

@Entity
@Table(name = "user")
@ApiModel(description = "Entity User Mapped to Database Table")
@Data
public class User {
    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The unique id of the user", position = 1, example = "1")
    private Long userId;

    @Column(name = "username", columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
    @ApiModelProperty(notes = "The user name. Max characters = 255", position = 2, example = "Alex")
    private String userName;

    @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
    @JsonIgnore
    @ApiModelProperty(notes = "The password. Max characters = 255", position = 3, example = "A5678")
    private String password;

    @Column(name = "fullname", columnDefinition = "VARCHAR(255)")
    @ApiModelProperty(notes = "The user full name", position = 5, example = "Alex Wilson")
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
