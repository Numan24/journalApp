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

  private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(6);

  private final UserOfJournalRepo userOfJournalRepo;

  public JournalUserService(UserOfJournalRepo userOfJournalRepo) {
    this.userOfJournalRepo = userOfJournalRepo;
  }

  public Page<UserOfJournal> getAllUsers(Pageable pageable) {
    return userOfJournalRepo.findAll(pageable);

  }

  public UserOfJournal saveJournalByUser(UserOfJournal dto) {
    UserOfJournal addedUser = userOfJournalRepo.save(dto);
    if (addedUser.getId() != null) {
      return addedUser;
    }
    return new UserOfJournal();
  }

  public UserOfJournal registerUser(UserOfJournal dto) {
    encodePasswordAndSetUserRole(dto);
    UserOfJournal addedUser = userOfJournalRepo.save(dto);
    return addedUser.getId() == null ? new UserOfJournal() : addedUser;
  }

  public UserOfJournal getUserById(ObjectId id) {
    Optional<UserOfJournal> foundUser = userOfJournalRepo.findById(id);
    if (foundUser.isPresent()) {
      return foundUser.get();
    }
    return new UserOfJournal();
  }

  public boolean deleteUserById(ObjectId id) {
    if (userOfJournalRepo.existsById(id)) {
      userOfJournalRepo.deleteById(id);
      return true;
    }
    return false;
  }

  public boolean deleteUserByName(String name) {
    long ans = userOfJournalRepo.deleteByUserName(name);
    return ans == 0;
  }


  public UserOfJournal update(UserOfJournal user) {
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

  public Page<UserOfJournal> allJournalEntriesOfUser(String userName, PageRequest pageRequest) {
    return userOfJournalRepo.findByUserName(userName, pageRequest);
  }

  public UserOfJournal getUserByName(String userName) {
    return userOfJournalRepo.findByUserName(userName);
  }

  private void encodePasswordAndSetUserRole(UserOfJournal userOfJournal) {
    userOfJournal.setPassword(passwordEncoder.encode(userOfJournal.getPassword()));
    userOfJournal.setRoles(List.of("USER"));
  }
}
