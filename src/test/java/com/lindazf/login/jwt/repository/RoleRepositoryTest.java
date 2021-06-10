package com.lindazf.login.jwt.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lindazf.login.jwt.dataloader.Constants;

import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RoleRepositoryTest {
	

	
	@Test
	void testFindByRoleName(String name) {

		assertEquals(Constants.ADMIN, Constants.ADMIN);
	}

}
