package com.lindazf.login.jwt.dataloader;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lindazf.login.jwt.entity.Role;
import com.lindazf.login.jwt.entity.User;
import com.lindazf.login.jwt.exception.ApplicationExceptionDetails;
import com.lindazf.login.jwt.service.UserService;

@Component
public class DataLoader {
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
		Role role1 = createRole(Constants.ADMIN, Constants.ADMIN_DESC);
		Role role2 = createRole(Constants.MANAGER, Constants.MANAGER_DESC);
		Role role3 = createRole(Constants.STAFF, Constants.STAFF_DESC);
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
		createUser(Constants.USER_ADMIN, Constants.PASSWORD, Constants.USER_ADMIN_NAME, Constants.ADMIN);
		createUser(Constants.USER_MANAGER, Constants.PASSWORD, Constants.USER_MANAGER_NAME, Constants.MANAGER);
		createUser(Constants.USER_STAFF, Constants.PASSWORD, Constants.USER_STAFF_NAME, Constants.STAFF);
		
	}
	
	private void createUser(String userName, String password, String fullName, String rolename) throws ApplicationExceptionDetails {
		User user = new User();
		user.setFullName(fullName);
		user.setUserName(userName);
		user.setPassword(password);
		Role role = userService.findByRoleName(rolename);
		user.setRole(role);
		userService.createUser(user);
		
	}
}
