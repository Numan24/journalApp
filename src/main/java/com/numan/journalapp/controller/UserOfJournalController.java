package com.numan.journalapp.controller;

import com.numan.journalapp.entity.UserOfJournal;
import com.numan.journalapp.service.JournalUserService;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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


@RestController
@RequestMapping("/user")
public class UserOfJournalController {

    private final JournalUserService userService;

    public UserOfJournalController(JournalUserService userService) {
        this.userService =  userService;
    }

    @GetMapping("{userName}")
    public ResponseEntity<Page<UserOfJournal>> getAllJournalEntriesOfUser(@PathVariable String userName,
                                                                          @RequestParam Integer count) {

        Page<UserOfJournal> userOfJournal = userService.allJournalEntriesOfUser(userName, PageRequest.of(count,
          10,
          Sort.Direction.DESC, "id"));

        return userOfJournal.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(userOfJournal);

    }

    @PostMapping("/{userId}")
    public ResponseEntity<UserOfJournal> findByJournalUserId(@PathVariable ObjectId userId) {
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @PostMapping
    public ResponseEntity<UserOfJournal> registerUser(@RequestBody UserOfJournal user) {
        return ResponseEntity.ok().body(userService.registerUser(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteByUserId(@PathVariable ObjectId userId) {
        return ResponseEntity.ok().body(userService.deleteUserById(userId) ? "successfully deleted" : "not deleted");
    }

    @PutMapping("/{userName}")
    public ResponseEntity<UserOfJournal> updateUserOfJournal(@RequestBody UserOfJournal user,
                                                            @PathVariable String userName) {
        return ResponseEntity.ok().body(userService.update(user, userName));
    }








}
