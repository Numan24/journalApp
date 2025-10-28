package com.numan.journalapp.service;

import com.numan.journalapp.entity.UserOfJournal;
import com.numan.journalapp.repo.UserOfJournalRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserOfJournalRepo userOfJournalRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserOfJournal userOfJournal = userOfJournalRepo.findByUserName(username);

    if (userOfJournal != null) {
      return User.builder()
        .username(userOfJournal.getUserName())
        .password(userOfJournal.getPassword())
        .authorities(userOfJournal.getRoles().stream().map(role -> "ROLE_" + role).toArray(String[]::new))
        .build();
    }

    throw new UsernameNotFoundException("User not found with user name: " + username);
  }
}
