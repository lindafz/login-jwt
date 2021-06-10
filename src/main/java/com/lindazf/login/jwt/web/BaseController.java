package com.lindazf.login.jwt.web;

import com.lindazf.login.jwt.entity.User;
import com.lindazf.login.jwt.exception.ApplicationExceptionDetails;
import com.lindazf.login.jwt.exception.ErrorCode;
import com.lindazf.login.jwt.exception.ErrorMessage;
import com.lindazf.login.jwt.security.ApplicationUserDetailService;
import com.lindazf.login.jwt.security.JwtProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public abstract class BaseController {
	private static final Logger log = LoggerFactory.getLogger(BaseController.class);
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ApplicationUserDetailService userDetailsService;

	Optional<String> getBearerToken() {
		String headerValue = request.getHeader(JwtProperties.AUTHORIZATION);
		if (headerValue != null && headerValue.startsWith(JwtProperties.BEARER)) {
			return Optional.of(headerValue.replace(JwtProperties.BEARER, "").trim());
		}
		return Optional.empty();
	}

	String getUserNameByToken() {
		return userDetailsService.getUserName();
	}

	User getUser() throws ApplicationExceptionDetails {
		String token = getBearerToken().orElseThrow(
				() -> new ApplicationExceptionDetails(ErrorMessage.TOKEN_NOT_VALID, ErrorCode.BAD_REQUEST));
		User user = userDetailsService.loadUserByToken(token).orElseThrow(
				() -> new ApplicationExceptionDetails(ErrorMessage.TOKEN_NOT_VALID, ErrorCode.BAD_REQUEST));
		return user;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public String return400(NoSuchElementException ex) {
		log.error("Unable to complete transaction", ex);
		return ex.getMessage();
	}
}
