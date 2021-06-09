package com.lindazf.login.jwt.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

@Entity
@Table(name = "role")
@ApiModel(description = "Entity Role Mapped to Database Table")
@Data
public class Role implements GrantedAuthority {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "role_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The unique id of the role", position = 1, example = "1")
    @JsonIgnore
    private Long roleId;

    @Column(name = "role_name", columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
    @ApiModelProperty(notes = "The role name. Max characters = 255", position = 2, example = "ROLE_ADMIN")
    private String roleName;

    @Column(name = "role_description", columnDefinition = "VARCHAR(255)")
    @ApiModelProperty(notes = "The role description. Max characters = 255", position = 3, example = "Sky Admin")
    private String roleDescription;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return roleName;
    }
}
