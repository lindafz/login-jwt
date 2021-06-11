package com.lindazf.login.jwt.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lindazf.login.jwt.entity.Role;
import com.lindazf.login.jwt.utils.TestDataConstant;

//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RoleRepositoryTest {
		
//	@Autowired
//	RoleRepository roleRepository;
//	
//	@Autowired
//	private TestEntityManager entityManager;
//	
//	private String roleName = TestDataConstant.TEST_ROLE_NAME;
	
	@Test
	void testFindByRoleName() {
//		Role role = new Role();
//		role.setRoleName(TestDataConstant.TEST_ROLE_NAME);
//		entityManager.persist(role);
//		Optional<Role> roleOptional = roleRepository.findByRoleName(roleName);
//		assertEquals(roleName, roleOptional.get().getRoleName());
	}
}
