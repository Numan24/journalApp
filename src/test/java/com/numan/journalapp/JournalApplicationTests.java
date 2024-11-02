package com.numan.journalapp;

import com.numan.journalapp.service.JournalUserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class JournalApplicationTests {

	@Autowired
	JournalUserService userService;

	@Disabled
	@ParameterizedTest
	@CsvSource({
		"Numan",
		"Ali",
		"Mahmud"
	})
	void testMembers(String userName) {
		assertNotNull(userService.getUserByName(userName));
	}

}
