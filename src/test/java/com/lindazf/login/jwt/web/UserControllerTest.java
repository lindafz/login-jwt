package com.lindazf.login.jwt.web;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lindazf.login.jwt.entity.Role;
import com.lindazf.login.jwt.entity.User;
import com.lindazf.login.jwt.service.UserService;
import com.lindazf.login.jwt.utils.TestDataConstant;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Mock
	UserService userService;

	@InjectMocks
	UserController userController;

	List<User> users;

	Optional<User> optionalUser;

	Optional<Role> optionalRole;

	MockMvc mockMvc;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		Role role = new Role();
		role.setRoleName(TestDataConstant.TEST_ROLE_NAME);
		optionalRole = Optional.of(role);

		User user = new User();
		user.setRole(role);
		user.setUserName(TestDataConstant.TEST_USER_NAME);
		user.setPassword(TestDataConstant.TEST_PASSWORD);
		optionalUser = Optional.of(user);

		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void findAllUsers() throws Exception {
		List<User> users = new ArrayList<>();
		users.add(optionalUser.get());
		when(userService.findAllUsers()).thenReturn(users);
		// when
		mockMvc.perform(get("/api/users")).andExpect(status().isOk());

		// then
		verify(userService, times(1)).findAllUsers();
	}
}
