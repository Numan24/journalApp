package com.numan.journalapp.repo;

import com.numan.journalapp.entity.UserOfJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserOfJournalRepoImpl {

  @Autowired
  private MongoTemplate mongoTemplate;

  public List<UserOfJournal> getUserForSA() {
    Query query = new Query();
    query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9_.%+-]+@[a-zA-Z0-9-.]+\\.[a-z|A-Z]{2,6}$"));
    query.addCriteria(Criteria.where("isSentimentAnalysis").is(true));
    return mongoTemplate.find(query, UserOfJournal.class);
  }
}
