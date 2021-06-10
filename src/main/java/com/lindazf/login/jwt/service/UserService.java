package com.lindazf.login.jwt.service;

import com.lindazf.login.jwt.entity.Role;
import com.lindazf.login.jwt.entity.User;
import com.lindazf.login.jwt.exception.ApplicationExceptionDetails;
import com.lindazf.login.jwt.exception.ErrorCode;
import com.lindazf.login.jwt.exception.ErrorMessage;
import com.lindazf.login.jwt.model.UserResponse;
import com.lindazf.login.jwt.repository.RoleRepository;
import com.lindazf.login.jwt.repository.UserRepository;
import com.lindazf.login.jwt.security.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
  
    private static final String INVALID_USER = "INVALID_USER";
    private static final String ENTITY_CREATE = "Create: ";
    private static final String ENTITY_UPDATE = "Update: ";
//    private static final String ENTITY_DELETE = "Delete: ";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserResponse signin(String username, String password) {
        log.info("New user attempting to sign in");
        List<Role> roles = new ArrayList<>();
        UserResponse response = new UserResponse();
        Optional<User> existedUser = findByUserName(username);
        if (!existedUser.isPresent()) {
            response.setAuthCode(INVALID_USER);
            response.setAuthorized(false);
        } else {
            User user = existedUser.get();
            roles.add(user.getRole());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = jwtProvider.createToken(username, roles);
            int validMinutes = jwtProvider.getValidMinutes();

            response.setAuthCode(token);
            response.setValidMinutes(validMinutes);

            response.setAuthorized(true);
        }
        return response;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    
    public void saveUsers(List<User> users) {
    	userRepository.saveAll(users);
    }
    
    public List<Role> findAllRoles(){
    	return roleRepository.findAll();
    }
    
    public void saveRoles(List<Role> roles) {
    	roleRepository.saveAll(roles);
    }

    public Role findByRoleName(String name) {
        return roleRepository.findByRoleName(name).get();
    }

    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void deleteUserById(Long userId) throws NoSuchElementException {
        log.warn("Delete user for userId = " + userId);
        userRepository.deleteById(userId);
    }

    public String createUser(User user) throws ApplicationExceptionDetails {
        userRepository.save(user);
        String result = ENTITY_CREATE + user.getUserName();
        log.info(result);
        return result;
    }

    public String updateUser(User dto) throws ApplicationExceptionDetails {
               String userName = dto.getUserName();
        User user = getUserByUsername(userName).orElseThrow(() -> new ApplicationExceptionDetails(ErrorMessage.USER_NOT_EXIST + ", username = " + userName, ErrorCode.NOT_FOUND));
        userRepository.save(user);
        String result = ENTITY_UPDATE + user.getUserName();
        log.info(result );
        return result;
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }
}
