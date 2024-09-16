package com.numan.journalapp.service;

import com.numan.journalapp.entity.UserOfJournal;
import com.numan.journalapp.repo.UserOfJournalRepo;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserOfJournalService {

  private final UserOfJournalRepo userOfJournalRepo;

  public UserOfJournalService(UserOfJournalRepo userOfJournalRepo) {
    this.userOfJournalRepo = userOfJournalRepo;
  }

  public Page<UserOfJournal> getAllUsers(Pageable pageable) {
    return userOfJournalRepo.findAll(pageable);

  }

  public UserOfJournal saveUser(UserOfJournal dto) {

    UserOfJournal addedUser = userOfJournalRepo.save(dto);
    if (addedUser.getId() != null) {
      return addedUser;
    }
    return UserOfJournal.builder().build();
  }

  public UserOfJournal getUserById(ObjectId id) {
    Optional<UserOfJournal> foundUser = userOfJournalRepo.findById(id);
    if (foundUser.isPresent()) {
      return foundUser.get();
    }
    return UserOfJournal.builder().build();
  }

  public boolean deleteUserById(ObjectId id) {
    if (userOfJournalRepo.existsById(id)) {
      userOfJournalRepo.deleteById(id);
      return true;
    }
    return false;
  }


  public UserOfJournal update(UserOfJournal user, String userName) {
    UserOfJournal oldUser = userOfJournalRepo.findByUserName(userName);
    UserOfJournal newUser = null;

    if(oldUser != null) {
      oldUser.setUserName(user.getUserName());
      oldUser.setPassword(user.getPassword());
      newUser = userOfJournalRepo.save(oldUser);
    }

    return newUser;

  }
}
