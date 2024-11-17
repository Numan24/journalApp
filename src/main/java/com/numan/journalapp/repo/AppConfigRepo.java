package com.numan.journalapp.repo;

import com.numan.journalapp.entity.AppConfigEntity;
import com.numan.journalapp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface AppConfigRepo extends MongoRepository<AppConfigEntity, ObjectId> {

}

