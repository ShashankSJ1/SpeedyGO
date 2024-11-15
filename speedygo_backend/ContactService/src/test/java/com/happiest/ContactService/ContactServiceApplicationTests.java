package com.happiest.ContactService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ContactServiceApplicationTests {

	@Test
	void contextLoads() {
		// This test ensures that the Spring application context is loaded correctly
	}


}
