package com.lindazf.login.jwt.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationExceptionDetails extends ApplicationException{
    private static final long serialVersionUID = -8221937288616228198L;
    private String type;

    public ApplicationExceptionDetails(String message, String type){
        super(message);
        this.type = type;
    }
}
