package com.numan.journalapp.repo;

import com.numan.journalapp.entity.UserOfJournal;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;


@Repository
public interface UserOfJournalRepo extends MongoRepository<UserOfJournal, ObjectId> {

  @Override
  @NonNull
  Page<UserOfJournal> findAll(@NonNull Pageable pageable);

  UserOfJournal findByUserName(String userName);

  Page<UserOfJournal> findByUserName(String userName, Pageable pageable);

  long deleteByUserName(String userName);
}

