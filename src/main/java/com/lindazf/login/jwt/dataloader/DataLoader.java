package com.lindazf.login.jwt.dataloader;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lindazf.login.jwt.entity.Role;
import com.lindazf.login.jwt.entity.User;
import com.lindazf.login.jwt.exception.ApplicationExceptionDetails;
import com.lindazf.login.jwt.model.UserDto;
import com.lindazf.login.jwt.service.UserService;
import com.lindazf.login.jwt.utils.Constant;

@Component
public class DataLoader {
	private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
	
	@Autowired
	private UserService userService;
			
	@PostConstruct
	public void loadData() throws ApplicationExceptionDetails{
		List<Role> roleList = userService.findAllRoles();
		List<User> userList = userService.findAllUsers();
		if(roleList.isEmpty()) {
			loadRoles();
		}
		
		if(userList.isEmpty()) {
			loadUsers();
		}
		
	}
	
	private void loadRoles() {
		List<Role> roles = new ArrayList<>();
		Role role1 = createRole(Constant.ADMIN, Constant.ADMIN_DESC);
		Role role2 = createRole(Constant.MANAGER, Constant.MANAGER_DESC);
		Role role3 = createRole(Constant.STAFF, Constant.STAFF_DESC);
		roles.add(role1);
		roles.add(role2);
		roles.add(role3);
		userService.saveRoles(roles);
	}
	
	private Role createRole(String name, String desc) {
		Role role = new Role();
		role.setRoleName(name);
		role.setRoleDescription(desc);
		return role;
		
	}
	
	private void loadUsers() throws ApplicationExceptionDetails{
		createUser(Constant.USER_ADMIN, Constant.PASSWORD, Constant.USER_ADMIN_NAME, Constant.ADMIN);
		createUser(Constant.USER_MANAGER, Constant.PASSWORD, Constant.USER_MANAGER_NAME, Constant.MANAGER);
		createUser(Constant.USER_STAFF, Constant.PASSWORD, Constant.USER_STAFF_NAME, Constant.STAFF);
		
	}
	
	private void createUser(String userName, String password, String fullName, String roleName) throws ApplicationExceptionDetails {
		UserDto dto = new UserDto();
		dto.setFullName(fullName);
		dto.setUserName(userName);
		log.info("password = " + password);
		dto.setPassword(password);
		dto.setRoleName(roleName);	
		userService.createUser(dto);
		
	}
}
