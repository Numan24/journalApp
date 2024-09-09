package com.numan.journalapp.service;

import com.numan.journalapp.dto.JournalRequestDTO;
import com.numan.journalapp.entity.JournalEntry;
import com.numan.journalapp.repo.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

  private final JournalEntryRepo journalEntryRepo;

  public JournalEntryService(JournalEntryRepo journalEntryRepo) {
    this.journalEntryRepo = journalEntryRepo;
  }

  public List<JournalEntry> getAllJournals() {
    return journalEntryRepo.findAll();
  }

  public JournalEntry saveJournal(JournalRequestDTO dto) {
    JournalEntry journalEntry = JournalEntry.builder()
      .id(dto.getId())
      .date(LocalDateTime.now())
      .title(dto.getTitle())
      .content(dto.getContent())
      .build();

    return journalEntryRepo.save(journalEntry);
  }

  public JournalEntry getJournalById(ObjectId id) {
    Optional<JournalEntry> journalEntry = journalEntryRepo.findById(id);
    return journalEntry.orElse(null);
  }

  public boolean deleteJournalById(ObjectId id) {
    if (journalEntryRepo.existsById(id)) {
      journalEntryRepo.deleteById(id);
      return true;
    }
    return false;
  }

  public JournalEntry updateJournalById(JournalRequestDTO dto, ObjectId id) {
    JournalEntry oldEntry = journalEntryRepo.findById(id).orElse(null);
    if (oldEntry != null) {
      oldEntry.setContent(dto.getContent() != null && !dto.getContent().isEmpty() ? dto.getContent() : oldEntry.getContent());
      oldEntry.setTitle(dto.getTitle() != null && !dto.getTitle().isEmpty() ? dto.getTitle() : oldEntry.getTitle());
      oldEntry.setDate(LocalDateTime.now());
      journalEntryRepo.save(oldEntry);
    }
    return oldEntry;
  }

}
