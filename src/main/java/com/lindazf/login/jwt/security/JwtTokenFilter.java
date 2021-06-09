package com.lindazf.login.jwt.security;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

public class JwtTokenFilter extends GenericFilterBean {
    private ApplicationUserDetailService userDetailsService;
//    private JwtInvalidTokenCache cache;

    private static final Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);

    public JwtTokenFilter(ApplicationUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("Process request to check for a JSON Web Token ");
        // Check for Authorization:Bearer JWT
        String headerValue = ((HttpServletRequest) req).getHeader(JwtProperties.AUTHORIZATION);
        getBearerToken(headerValue).ifPresent(token -> {
            // Pull the Username and Roles from the JWT to construct the user details
            userDetailsService.loadUserByJwtToken(token).ifPresent(userDetails -> {

                    String userName = userDetails.getUsername();
                    log.info("valid token for user =" + userDetails.getUsername());
                    userDetailsService.setUserName(userName);
                    // Add the user details (Permissions) to the Context for just API invocation
                    SecurityContextHolder.getContext().setAuthentication(
                            new PreAuthenticatedAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
                    userDetailsService.setUserDetails(userDetails);

            });

        });

        // move on to the next filter in the chains
        filterChain.doFilter(req, res);
    }

    /**
     * if present, extract the jwt token from the "Bearer <jwt>" header value.
     *
     * @param headerVal
     * @return jwt if present, empty otherwise
     */
    private Optional<String> getBearerToken(String headerVal) {
        log.info("header value = " + headerVal);
        if (headerVal != null && headerVal.startsWith(JwtProperties.BEARER)) {
            return Optional.of(headerVal.replace(JwtProperties.BEARER, "").trim());
        }
        return Optional.empty();
    }

}
