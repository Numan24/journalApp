package com.numan.journalapp.service;

import com.numan.journalapp.dto.JournalRequestDTO;
import com.numan.journalapp.dto.JournalResponseDTO;
import com.numan.journalapp.entity.JournalEntry;
import com.numan.journalapp.repo.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

  private final JournalEntryRepo journalEntryRepo;

  public JournalEntryService(JournalEntryRepo journalEntryRepo) {
    this.journalEntryRepo = journalEntryRepo;
  }

  public List<JournalResponseDTO> getAllJournals() {
    List<JournalEntry> entries = journalEntryRepo.findAll();
    List<JournalResponseDTO> responseList = new ArrayList<>();
    if (!entries.isEmpty()) {
      entries.forEach(journalEntry -> responseList.add(
          JournalResponseDTO.builder().title(journalEntry.getTitle())
            .content(journalEntry.getContent())
            .date(journalEntry.getDate())
            .build()
          ));
    }
    return responseList;
  }

  public JournalResponseDTO saveJournal(JournalRequestDTO dto) {
    JournalEntry journalEntry = JournalEntry.builder()
      .id(dto.getId())
      .date(LocalDateTime.now())
      .title(dto.getTitle())
      .content(dto.getContent())
      .build();

    JournalEntry addedEntry = journalEntryRepo.save(journalEntry);
    if (addedEntry.getId() != null) {
      return JournalResponseDTO.builder()
        .title(addedEntry.getTitle())
        .content(addedEntry.getTitle())
        .date(addedEntry.getDate())
        .build();
    }
    return JournalResponseDTO.builder().build();
  }

  public JournalResponseDTO getJournalById(ObjectId id) {
    Optional<JournalEntry> journalEntry = journalEntryRepo.findById(id);
    if (journalEntry.isPresent()) {
      return JournalResponseDTO.builder()
        .content(journalEntry.get().getContent())
        .title(journalEntry.get().getTitle())
        .date(journalEntry.get().getDate())
        .build();
    }
    return JournalResponseDTO.builder().build();
  }

  public boolean deleteJournalById(ObjectId id) {
    if (journalEntryRepo.existsById(id)) {
      journalEntryRepo.deleteById(id);
      return true;
    }
    return false;
  }

  public JournalResponseDTO updateJournalById(JournalRequestDTO dto, ObjectId id) {
    JournalEntry oldEntry = journalEntryRepo.findById(id).orElse(null);
    JournalEntry newEntry = JournalEntry.builder().build();
    if (oldEntry != null) {
      oldEntry.setContent(dto.getContent() != null && !dto.getContent().isEmpty() ? dto.getContent() : oldEntry.getContent());
      oldEntry.setTitle(dto.getTitle() != null && !dto.getTitle().isEmpty() ? dto.getTitle() : oldEntry.getTitle());
      oldEntry.setDate(LocalDateTime.now());
      newEntry = journalEntryRepo.save(oldEntry);
    }
    if (newEntry.getId() != null) {
      return JournalResponseDTO.builder()
        .title(newEntry.getTitle())
        .date(newEntry.getDate())
        .content(newEntry.getContent())
        .build();
    }
    return JournalResponseDTO.builder().build();
  }

}
