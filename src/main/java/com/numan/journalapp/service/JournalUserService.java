package com.numan.journalapp.service;

import com.numan.journalapp.entity.UserOfJournal;
import com.numan.journalapp.repo.UserOfJournalRepo;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalUserService {

  private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);

  private final UserOfJournalRepo userOfJournalRepo;

  public JournalUserService(final UserOfJournalRepo userOfJournalRepo) {
    this.userOfJournalRepo = userOfJournalRepo;

  }

  public Page<UserOfJournal> getAllUsers(final Pageable pageable) {
    return userOfJournalRepo.findAll(pageable);

  }

  public UserOfJournal saveJournalByUser(final UserOfJournal dto) {
    UserOfJournal addedUser = userOfJournalRepo.save(dto);
    if (addedUser.getId() != null) {
      return addedUser;
    }
    return new UserOfJournal();

  }

  public UserOfJournal registerUser(final UserOfJournal dto) {
    encodePasswordAndSetUserRole(dto);
    UserOfJournal addedUser = userOfJournalRepo.save(dto);
    return addedUser.getId() == null ? new UserOfJournal() : addedUser;

  }

  public UserOfJournal getUserById(final ObjectId id) {
    Optional<UserOfJournal> foundUser = userOfJournalRepo.findById(id);
    if (foundUser.isPresent()) {
      return foundUser.get();
    }
    return new UserOfJournal();

  }

  public boolean deleteUserByName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    long ans = userOfJournalRepo.deleteByUserName(userName);
    return ans == 0;

  }

  public UserOfJournal update(final UserOfJournal user) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    UserOfJournal oldUser = userOfJournalRepo.findByUserName(userName);
    UserOfJournal newUser = null;

    if(oldUser != null) {
      oldUser.setUserName(user.getUserName());
      encodePasswordAndSetUserRole(user);
      newUser = userOfJournalRepo.save(oldUser);
    }
    return newUser;

  }

  public Page<UserOfJournal> allJournalEntriesOfUser(final PageRequest pageRequest) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    return userOfJournalRepo.findByUserName(userName, pageRequest);

  }

  public UserOfJournal getUserByName(final String userName) {
    return userOfJournalRepo.findByUserName(userName);

  }

  private void encodePasswordAndSetUserRole(final UserOfJournal userOfJournal) {
    userOfJournal.setPassword(passwordEncoder.encode(userOfJournal.getPassword()));
    userOfJournal.setRoles(List.of("USER"));

  }

  public UserOfJournal findingLoggedInUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    return getUserByName(userName);
  }

}
