package com.numan.journalapp.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class JournalResponseDTO {

  private String title;
  private String content;
  private LocalDateTime date;
}
