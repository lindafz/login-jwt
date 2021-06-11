package com.lindazf.login.jwt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;

import com.lindazf.login.jwt.entity.Role;
import com.lindazf.login.jwt.entity.User;
import com.lindazf.login.jwt.exception.ApplicationExceptionDetails;
import com.lindazf.login.jwt.repository.RoleRepository;
import com.lindazf.login.jwt.repository.UserRepository;
import com.lindazf.login.jwt.security.JwtProvider;
import com.lindazf.login.jwt.utils.TestDataConstant;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

	@Mock
	private UserRepository userRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private JwtProvider jwtProvider;

	@Mock
	private AuthenticationManager authenticationManager;

	@InjectMocks
	UserService userService;

	private Optional<User> optionalUser;
	private Optional<Role> optionalRole;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	@Deprecated
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Role role = new Role();
		role.setRoleName(TestDataConstant.TEST_ROLE_NAME);
		optionalRole = Optional.of(role);

		User user = new User();
		user.setRole(role);
		user.setUserName(TestDataConstant.TEST_USER_NAME);
		user.setPassword(TestDataConstant.TEST_PASSWORD);
		optionalUser = Optional.of(user);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void findAllUsers() {
		List<User> returnedUserList = new ArrayList<>();
		Role role = new Role();
		role.setRoleName(TestDataConstant.TEST_ROLE_NAME);
		User user1 = new User();
		User user2 = new User();
		user1.setUserId(1L);
		user1.setUserName(TestDataConstant.TEST_USER_NAME);
		user1.setPassword(TestDataConstant.TEST_PASSWORD);
		user1.setRole(role);

		user2.setUserId(2L);
		user2.setUserName(TestDataConstant.TEST_USER2_NAME);
		user2.setRole(role);
		user2.setPassword(TestDataConstant.TEST_PASSWORD);

		returnedUserList.add(user1);
		returnedUserList.add(user2);

		when(userRepository.findAll()).thenReturn(returnedUserList);
		List<User> users = userService.findAllUsers();
		log.debug("User size = " + returnedUserList.size());
		assertEquals(2, users.size());
	}

	@Test
	void findAllRoles() {
		List<Role> returnedRoleList = new ArrayList<>();
		Role role1 = new Role();
		Role role2 = new Role();

		role1.setRoleId(1L);
		role1.setRoleName(TestDataConstant.TEST_ROLE_NAME);

		role2.setRoleId(2L);
		role2.setRoleName(TestDataConstant.TEST_ROLE2_NAME);

		returnedRoleList.add(role1);
		returnedRoleList.add(role2);

		when(roleRepository.findAll()).thenReturn(returnedRoleList);
		List<Role> roles = userService.findAllRoles();
		log.debug("role size = " + returnedRoleList.size());
		assertEquals(2, roles.size());
	}

	@Test
	void saveRoles() {
		List<Role> returnedRoleList = new ArrayList<>();

		Role role1 = new Role();
		Role role2 = new Role();

		role1.setRoleId(1L);
		role1.setRoleName(TestDataConstant.TEST_ROLE_NAME);

		role2.setRoleId(2L);
		role2.setRoleName(TestDataConstant.TEST_ROLE2_NAME);

		returnedRoleList.add(role1);
		returnedRoleList.add(role2);

		when(roleRepository.saveAll(any())).thenReturn(returnedRoleList);
		List<Role> roles = userService.saveRoles(returnedRoleList);

		assertNotNull(roles);
		verify(roleRepository).saveAll(any());
	}

	@Test
	void findByRoleName() {
		when(roleRepository.findByRoleName(anyString())).thenReturn(optionalRole);
		Role currentRole = userService.findByRoleName(TestDataConstant.TEST_ROLE_NAME);
		log.info("find role: " + currentRole.getRoleName());
		assertEquals(currentRole.getRoleName(), TestDataConstant.TEST_ROLE_NAME);
	}

	@Test
	void findByUserName() {
		when(userRepository.findByUserName(anyString())).thenReturn(optionalUser);
		Optional<User> currentUser = userService.findByUserName(TestDataConstant.TEST_USER_NAME);
		log.info("find user: " + currentUser.get().getUserName());
		assertEquals(currentUser.get().getUserName(), TestDataConstant.TEST_USER_NAME);
	}

	@Test
	void deleteUserById() {
		userService.deleteUserById(1L);
		verify(userRepository).deleteById(anyLong());
	}

	@Test
	void createUser() throws ApplicationExceptionDetails {
		User user = new User();
		user.setRole(optionalRole.get());
		user.setUserId(1L);
		user.setUserName(TestDataConstant.TEST_USER_NAME);
		user.setPassword(TestDataConstant.TEST_PASSWORD);

		when(userRepository.save(any())).thenReturn(optionalUser.get());
		user = userService.createUser(user);

		assertNotNull(user);
		verify(userRepository).save(any());
	}

}