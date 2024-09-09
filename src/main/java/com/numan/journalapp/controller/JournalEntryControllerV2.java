package com.numan.journalapp.controller;

import com.numan.journalapp.dto.JournalRequestDTO;
import com.numan.journalapp.entity.JournalEntry;
import com.numan.journalapp.service.JournalEntryService;
import org.bson.types.ObjectId;
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

@RestController
@RequestMapping("/journalv2")
public class JournalEntryControllerV2 {

    private final JournalEntryService journalEntryService;

    public JournalEntryControllerV2(JournalEntryService journalEntryService) {
        this.journalEntryService = journalEntryService;
    }

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllEntries() {
        return ResponseEntity.ok().body(journalEntryService.getAllJournals());
    }

    @PostMapping
    public ResponseEntity<JournalEntry> saveJournal(@RequestBody JournalRequestDTO dto) {
        return ResponseEntity.ok().body(journalEntryService.saveJournal(dto));
    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> getEntry(@PathVariable ObjectId journalId) {
        return journalEntryService.getJournalById(journalId) == null
          ? ResponseEntity.notFound().build()
          : ResponseEntity.ok().body(journalEntryService.getJournalById(journalId));
    }

    @DeleteMapping("/id/{journalId}")
    public ResponseEntity<String> deleteJournalById(@PathVariable ObjectId journalId) {
       return journalEntryService.deleteJournalById(journalId)
         ? ResponseEntity.ok().body("Successfully Deleted")
         : ResponseEntity.notFound().build();

    }


    @PutMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> updateJournal(@RequestBody JournalRequestDTO dto, @PathVariable ObjectId journalId) {
        return ResponseEntity.ok().body(journalEntryService.updateJournalById(dto, journalId));

    }




}
