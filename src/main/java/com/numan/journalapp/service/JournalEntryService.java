package com.numan.journalapp.service;

import com.numan.journalapp.dto.JournalRequestDTO;
import com.numan.journalapp.dto.JournalResponseDTO;
import com.numan.journalapp.entity.JournalEntry;
import com.numan.journalapp.entity.UserOfJournal;
import com.numan.journalapp.repo.JournalEntryRepo;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

  private final JournalEntryRepo journalEntryRepo;
  private final JournalUserService userService;

  public JournalEntryService(JournalEntryRepo journalEntryRepo,
                             JournalUserService journalUserService) {
    this.journalEntryRepo = journalEntryRepo;
    this.userService = journalUserService;
  }

  public Page<JournalResponseDTO> getAllJournals(Pageable pageable) {
    Page<JournalEntry> entries = journalEntryRepo.findAll(pageable);
    Page<JournalResponseDTO> responseList = new PageImpl<>(new ArrayList<>());
    if (!entries.isEmpty()) {
      responseList = entries.map(journalEntry ->
          JournalResponseDTO.builder()
            .title(journalEntry.getTitle())
            .content(journalEntry.getContent())
            .date(journalEntry.getDate())
            .build()
          );
    }
    return responseList;
  }


  public List<JournalEntry> getAllJournalsOfUser(String userName) {
    UserOfJournal user = userService.getUserByName(userName);
    List<JournalEntry> entries = user.getJournalEntry();
    return entries.isEmpty() ? new ArrayList<>() : entries;
  }

  public JournalResponseDTO saveJournal(JournalRequestDTO dto) {
    JournalEntry journalEntry = mapDtoToJournalEntry(dto);

    JournalEntry addedEntry = journalEntryRepo.save(journalEntry);
    if (addedEntry.getId() != null) {
      return JournalResponseDTO.builder()
        .title(addedEntry.getTitle())
        .content(addedEntry.getContent())
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

  public boolean saveJournalByUser(@Valid JournalRequestDTO dto, String userName) {
    UserOfJournal user = userService.getUserByName(userName);

    JournalEntry journalEntry = mapDtoToJournalEntry(dto);

    JournalEntry savedJournalEntry = journalEntryRepo.save(journalEntry);

    user.getJournalEntry().add(savedJournalEntry);

    return userService.saveUser(user).getId() == null;
  }

  private JournalEntry mapDtoToJournalEntry(JournalRequestDTO dto) {
    return JournalEntry.builder()
      .id(dto.getId())
      .date(LocalDateTime.now())
      .title(dto.getTitle())
      .content(dto.getContent())
      .build();
  }

}
