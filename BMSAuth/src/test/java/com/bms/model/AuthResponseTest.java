
package com.bms.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AuthResponseTest {

	AuthResponse auth = new AuthResponse();
	AuthResponse auth1 = new AuthResponse("kumar", "kumar@792", true);

	@Test
	void testUsername() {
		auth.setUsername("kumar");
		assertEquals( "kumar", auth.getUsername());
	}

	@Test
	void testName() {
		auth.setMessage("Name");
		assertEquals("Name", auth.getMessage());
	}

	@Test
	void testIsValid() {
		auth.setValid(true);
		assertEquals( true, auth.isValid());
	}

}
