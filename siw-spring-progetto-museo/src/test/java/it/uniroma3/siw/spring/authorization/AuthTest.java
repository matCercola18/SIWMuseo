package it.uniroma3.siw.spring.authorization;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthTest {

	static PasswordEncoder passwordEncoder;
	
	@BeforeAll
	public static void setup() {
		passwordEncoder=new BCryptPasswordEncoder();
	}
	@Test
	void test() {
		System.out.println(passwordEncoder.encode("admin"));
	}

}
