package com.numan.journalapp;

import com.numan.journalapp.entity.UserOfJournal;
import com.numan.journalapp.repo.UserOfJournalRepoImpl;
import com.numan.journalapp.service.JournalUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ControllerImplTests {

	@Autowired
	private UserOfJournalRepoImpl userOfJournalRepo;

	@Test
	public void testgetSA() {
		List<UserOfJournal> users = userOfJournalRepo.getUserForSA();
		Assertions.assertNotNull(users);
	}


}
