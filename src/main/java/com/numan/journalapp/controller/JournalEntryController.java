package com.numan.journalapp.controller;

import com.numan.journalapp.entity.JournalEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private final Map<Long, JournalEntry> journals = new HashMap<>();

    @GetMapping
    public ResponseEntity<Map<Long, JournalEntry>> getAllEntries() {
        return ResponseEntity.ok().body(journals);
    }




}
