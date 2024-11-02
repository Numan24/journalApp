package com.numan.journalapp.service;

import com.numan.journalapp.repo.UserOfJournalRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class JournalUserServiceTests {

  @Autowired
  private UserOfJournalRepo userOfJournalRepo;

  @Disabled
  @ParameterizedTest
  @ValueSource(strings = {
    "Numan",
    "Mahmud",
    "Ali"
  })
  void testFindByUserName(String name) {
    assertNotNull(userOfJournalRepo.findByUserName(name), "failed for user name: " + name);
  }

  @Disabled
  @ParameterizedTest
  @CsvSource({
    "1,1,2",
    "2,2,4"
  })
  void testParams(int a, int b, int expected) {
    assertEquals(expected, a+b);
  }
 }
