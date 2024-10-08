package com.numan.journalapp.service;

import com.numan.journalapp.dto.JournalRequestDTO;
import com.numan.journalapp.dto.JournalResponseDTO;
import com.numan.journalapp.entity.JournalEntry;
import com.numan.journalapp.entity.UserOfJournal;
import com.numan.journalapp.repo.JournalEntryRepo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    if (user == null) {
      return new ArrayList<>();
    }
    List<JournalEntry> entries = user.getJournalEntries();
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
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    List<JournalEntry> journalEntries = getAllJournalsOfUser(userName);
    List<JournalEntry> requiredJournals = journalEntries.stream().filter(x -> x.getId().equals(id)).toList();
    if (!requiredJournals.isEmpty()) {
      return JournalResponseDTO.builder()
        .content(requiredJournals.get(0).getContent())
        .title(requiredJournals.get(0).getTitle())
        .date(requiredJournals.get(0).getDate())
        .build();
    }
    return JournalResponseDTO.builder().build();
  }

  public boolean deleteJournalById(ObjectId journalId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    UserOfJournal user = userService.getUserByName(userName);
    boolean ans = user.getJournalEntries().removeIf(x -> x.getId().equals(journalId));
    if (ans) {
      journalEntryRepo.deleteById(journalId);
      userService.saveJournalByUser(user);
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

  @Transactional
  public boolean saveJournalByUser(@Valid JournalRequestDTO dto) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String userName = authentication.getName();
      UserOfJournal user = userService.getUserByName(userName);
      JournalEntry journalEntry = mapDtoToJournalEntry(dto);
      JournalEntry savedJournalEntry = journalEntryRepo.save(journalEntry);
      user.getJournalEntries().add(savedJournalEntry);
      return userService.saveJournalByUser(user).getId() == null;
    } catch (RuntimeException re) {
      log.error(re.getMessage());
      log.error(re.toString());
      throw new RuntimeException("Error happened while saving", re);
    }
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
