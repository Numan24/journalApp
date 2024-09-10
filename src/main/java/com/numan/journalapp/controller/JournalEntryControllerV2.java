package com.numan.journalapp.controller;

import com.numan.journalapp.dto.JournalRequestDTO;
import com.numan.journalapp.dto.JournalResponseDTO;
import com.numan.journalapp.service.JournalEntryService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/journalv2")
public class JournalEntryControllerV2 {

    private final JournalEntryService journalEntryService;

    public JournalEntryControllerV2(JournalEntryService journalEntryService) {
        this.journalEntryService = journalEntryService;

    }

    @GetMapping
    public ResponseEntity<List<JournalResponseDTO>> getAllEntries() {
        List<JournalResponseDTO> entries = journalEntryService.getAllJournals();
        return entries == null || entries.isEmpty()
          ? ResponseEntity.notFound().build()
          : ResponseEntity.ok().body(entries);

    }

    @PostMapping
    public ResponseEntity<JournalResponseDTO> saveJournal(@RequestBody JournalRequestDTO dto) {
        try {
            JournalResponseDTO responseDTO = journalEntryService.saveJournal(dto);
            if (responseDTO.getTitle() == null) {
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.ok().body(responseDTO);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<JournalResponseDTO> getEntry(@PathVariable ObjectId journalId) {
        return journalEntryService.getJournalById(journalId) == null
          ? ResponseEntity.notFound().build()
          : ResponseEntity.ok().body(journalEntryService.getJournalById(journalId));

    }

    @DeleteMapping("/id/{journalId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId journalId) {
       return journalEntryService.deleteJournalById(journalId)
         ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully Deleted")
         : ResponseEntity.notFound().build();

    }

    @PutMapping("/id/{journalId}")
    public ResponseEntity<JournalResponseDTO> updateJournal(@RequestBody JournalRequestDTO dto, @PathVariable ObjectId journalId) {
        JournalResponseDTO responseDTO = journalEntryService.updateJournalById(dto, journalId);
        return responseDTO.getTitle() == null
          ? ResponseEntity.notFound().build()
          : ResponseEntity.ok().body(responseDTO);

    }




}
