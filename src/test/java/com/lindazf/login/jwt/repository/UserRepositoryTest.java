package com.lindazf.login.jwt.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lindazf.login.jwt.entity.Role;
import com.lindazf.login.jwt.entity.User;
import com.lindazf.login.jwt.utils.TestDataConstant;

//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {
//	@Autowired
//	private TestEntityManager entityManager;
//	
//	@Autowired
//	private UserRepository userRepository;
	
	@Test
	void testFindByUserName() {
//		Role role = new Role();
//		role.setRoleName(TestDataConstant.TEST_ROLE_NAME);
//		entityManager.persist(role);
//		User user = new User();
//		user.setRole(role);
//		user.setUserName(TestDataConstant.TEST_USER_NAME);
//		user.setPassword(TestDataConstant.TEST_PASSWORD);
//		entityManager.persist(user);
//		User newUser = userRepository.findByUserName(TestDataConstant.TEST_USER_NAME).get();
//		assertEquals(TestDataConstant.TEST_USER_NAME, newUser.getUserName());
		
	}
}
