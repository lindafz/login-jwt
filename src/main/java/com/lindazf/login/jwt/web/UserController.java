package com.lindazf.login.jwt.web;

import com.lindazf.login.jwt.entity.User;
import com.lindazf.login.jwt.exception.ApplicationException;
import com.lindazf.login.jwt.model.LoginDto;
import com.lindazf.login.jwt.model.UserDto;
import com.lindazf.login.jwt.model.UserResponse;
import com.lindazf.login.jwt.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api", produces = "application/json")
public class UserController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@PostMapping(value = "/login")
	@ApiOperation("Authenticate and authorize user")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		HttpHeaders responseHeaders = new HttpHeaders();
		ResponseEntity<?> responseEntity = null;
		UserResponse response = userService.signin(loginDto.getUserName(), loginDto.getPassword());
		if (response.isAuthorized()) {
			responseEntity = new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<>(response, responseHeaders, HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@DeleteMapping("/user/{userId}")
	@ApiOperation("Authorized Role- System Admin, Manager. Delete user by userId")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
	public void deleteUser(@PathVariable("userId") Long userId) throws ApplicationException {
		userService.deleteUserById(userId);
	}

	@GetMapping(value = "/user")
	@ApiOperation("Authorized All Roles")
	public ResponseEntity<?> getUserByToken() {
		HttpHeaders responseHeaders = new HttpHeaders();
		String userName = getUserNameByToken();
		log.debug("internal users operator name = " + userName);
		Optional<User> optionalUser = userService.findByUserName(userName);
		if (optionalUser.isPresent()) {
			return new ResponseEntity<>(optionalUser.get(), responseHeaders, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/user")
	@ApiOperation("Authorized All Roles for demo purpose")
	//For demo only. So anyone can create a new user
	public ResponseEntity<?> createUser(
			@ApiParam(name = "User create request", value = "The request body is a JSON value representing the user information", required = true) @RequestBody UserDto userDto)
			throws ApplicationException {
		log.debug("Create User Name : " + userDto.getUserName());
		HttpHeaders responseHeaders = new HttpHeaders();
		User newUser = userService.createUser(userDto);

		ResponseEntity<?> responseEntity = new ResponseEntity<>(newUser, responseHeaders, HttpStatus.CREATED);
		return responseEntity;
	}

	@PutMapping("/user")
	@ApiOperation("Authorized Role- System Admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateUsers(
			@ApiParam(name = "User edit request", value = "The request body is a JSON value representing the user information", required = true) @RequestBody UserDto dto)
			throws ApplicationException {
		log.debug("Update User Name : " + dto.getUserName());
		HttpHeaders responseHeaders = new HttpHeaders();
		User newUser = userService.updateUser(dto);

		ResponseEntity<?> responseEntity = new ResponseEntity<>(newUser, responseHeaders, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping(value = "/users")
	@ApiOperation("Authorized All Roles")
	public ResponseEntity<?> findAllUsers() {
		List<User> users = userService.findAllUsers();
		ResponseEntity<?> responseEntity = new ResponseEntity<>(users, new HttpHeaders(), HttpStatus.OK);
		return responseEntity;
	}

}
