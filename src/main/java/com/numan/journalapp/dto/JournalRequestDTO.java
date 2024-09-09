package com.numan.journalapp.dto;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class JournalRequestDTO {

  private ObjectId id;
  private String title;
  private String content;

}
