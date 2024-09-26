package com.numan.journalapp.controller;

import com.numan.journalapp.dto.JournalRequestDTO;
import com.numan.journalapp.dto.JournalResponseDTO;
import com.numan.journalapp.entity.JournalEntry;
import com.numan.journalapp.service.JournalEntryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * @deprecated
     */
    @GetMapping
    @Deprecated(since = "0.1", forRemoval = true)
    public ResponseEntity<Page<JournalResponseDTO>> getAllEntries(@RequestParam Integer numberOfPage) {
        return ResponseEntity.ok().body(journalEntryService.getAllJournals(PageRequest.of(numberOfPage,
          10,
          Sort.Direction.DESC, "id")));

    }

    @GetMapping("{userName}")
    public ResponseEntity<List<JournalEntry>> getAllEntriesOfUser(@PathVariable String userName) {
        List<JournalEntry> entries = journalEntryService.getAllJournalsOfUser(userName);
        return entries.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(entries);

    }


    @PostMapping("{userName}")
    public ResponseEntity<JournalResponseDTO> saveJournal(@Valid @RequestBody JournalRequestDTO dto,
                                                          @PathVariable String userName) {
        try {
            boolean res = journalEntryService.saveJournalByUser(dto, userName);
            if (res) {
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<JournalResponseDTO> getEntry(@PathVariable ObjectId journalId) {
        JournalResponseDTO responseDTO = journalEntryService.getJournalById(journalId);
        return responseDTO.getTitle() == null
          ? ResponseEntity.notFound().build()
          : ResponseEntity.ok().body(responseDTO);

    }

    @DeleteMapping("/id/{userName}/{journalId}")
    public ResponseEntity<String> deleteJournalById(@PathVariable String userName, @PathVariable ObjectId journalId) {
       return journalEntryService.deleteJournalById(journalId, userName)
         ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully Deleted")
         : ResponseEntity.notFound().build();

    }

    @PutMapping("/id/{journalId}")
    public ResponseEntity<JournalResponseDTO> updateJournal(@Valid@RequestBody JournalRequestDTO dto, @PathVariable ObjectId journalId) {
        JournalResponseDTO responseDTO = journalEntryService.updateJournalById(dto, journalId);
        return responseDTO.getTitle() == null
          ? ResponseEntity.notFound().build()
          : ResponseEntity.ok().body(responseDTO);

    }




}
