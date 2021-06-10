package com.lindazf.login.jwt.security;

import com.lindazf.login.jwt.entity.Role;
import com.lindazf.login.jwt.entity.User;
import com.lindazf.login.jwt.exception.ApplicationExceptionDetails;
import com.lindazf.login.jwt.exception.ErrorCode;
import com.lindazf.login.jwt.exception.ErrorMessage;
import com.lindazf.login.jwt.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

@Component
public class ApplicationUserDetailService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(ApplicationUserDetailService.class);
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    private UserService userService;
    private String userName;

    private User user;

    private UserDetails userDetails;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(String.format("Get user [%s] from database", username));
        List<Role> roleList = new ArrayList<>();
        User user = userService.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.USER_NOT_EXIST + "error code: " + ErrorCode.NOT_FOUND));
        Role role = user.getRole();
        roleList.add(role);

        setUser(user);
        // org.springframework.security.core.userdetails.User.withUsername() builder
        return withUsername(user.getUserName())//
                .password(user.getPassword())//
                .authorities(roleList)//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }

    public Optional<User> loadUserByToken(String token) {
        if (jwtProvider.isValidToken(token)) {
            String username = jwtProvider.getUsernameFromToken(token);
            Optional<User> optionalUser = Optional.of(userService.findByUserName(username).get());
            return optionalUser;
        }
        return Optional.empty();
    }

    /**
     * Extract username and roles from a validated jwt string.
     *
     * @param jwtToken jwt string
     * @return UserDetails if valid, Empty otherwise
     */
    public Optional<UserDetails> loadUserByJwtToken(String jwtToken) {
        if (jwtProvider.isValidToken(jwtToken)) {
            return Optional.of(withUsername(jwtProvider.getUsernameFromToken(jwtToken)) //
                    .authorities(jwtProvider.getRoles(jwtToken))//
                    .password("") // token does not have password but field may not be empty
                    .accountExpired(false)//
                    .accountLocked(false)//
                    .credentialsExpired(false)//
                    .disabled(false)//
                    .build());
        }
        return Optional.empty();
    }

    /**
     * Extract the username from the JWT then lookup the user in the database.
     *
     * @param jwtToken
     * @return
     */
    public Optional<UserDetails> loadUserByJwtTokenAndDatabase(String jwtToken) {
        if (jwtProvider.isValidToken(jwtToken)) {
            return Optional.of(loadUserByUsername(jwtProvider.getUsernameFromToken(jwtToken)));
        } else {
            return Optional.empty();
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getRoleList() throws ApplicationExceptionDetails {
        List<Role> roleList = new ArrayList<>();

        Role role = userService.findByUserName(userName).get().getRole();
        roleList.add(role);

        return roleList;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
