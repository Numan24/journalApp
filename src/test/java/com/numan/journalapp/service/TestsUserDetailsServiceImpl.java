package com.numan.journalapp.service;

import com.numan.journalapp.entity.UserOfJournal;
import com.numan.journalapp.repo.UserOfJournalRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class TestsUserDetailsServiceImpl {

  @InjectMocks
  private UserDetailsServiceImpl userDetailsService;

  @Mock
  private UserOfJournalRepo userOfJournalRepo;

  @BeforeEach
  void headStart() {
    MockitoAnnotations.initMocks(this);
  }

  @Disabled
  @Test
  void loadUserByUserNameTest() {

    when(userOfJournalRepo.findByUserName(ArgumentMatchers.anyString()))
      .thenReturn(UserOfJournal.builder()
        .userName("numan")
        .password("gfhjg")
        .roles(new ArrayList<>())
        .build());
    UserDetails userDetails = userDetailsService.loadUserByUsername("Numan");
    assertNotNull(userDetails);

  }
}
