package com.numan.journalapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class JournalRequestDTO {

  private ObjectId id;
  @NotBlank(message = "Required Parameter 'Title' must contain one non white space character")
  @Size(min = 1, max = 20, message = "Required Parameter 'Title' must be between 1-20 characters")
  private String title;
  @NotBlank(message = "Required Parameter 'content' must contain one non white space character")
  @Size(min = 1, max = 200, message = "Required Parameter 'content' must be between 1-200 characters")
  private String content;

}
