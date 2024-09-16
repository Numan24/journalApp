package com.numan.journalapp.controller;

import com.numan.journalapp.entity.UserOfJournal;
import com.numan.journalapp.service.UserOfJournalService;
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
@RequestMapping("/journal/user")
public class UserOfJournalController {

    private final UserOfJournalService userService;

    public UserOfJournalController(UserOfJournalService userService) {
        this.userService =  userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserOfJournal>> getAllUserOfJournal(@RequestParam Integer numPage) {
        return ResponseEntity.ok().body(userService.getAllUsers(PageRequest.of(numPage,
          10,
          Sort.Direction.DESC, "id")));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<UserOfJournal> findByJournalUserId(@PathVariable ObjectId userId) {
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @PostMapping
    public ResponseEntity<UserOfJournal> saveUserOfJournal(@RequestBody UserOfJournal user) {
        System.out.println("hi in save");
        return ResponseEntity.ok().body(userService.saveUser(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteByUserId(@PathVariable ObjectId userId) {
        return ResponseEntity.ok().body(userService.deleteUserById(userId) ? "successfully deleted" : "not deleted");
    }

    @PutMapping("/{userName}")
    public ResponseEntity<UserOfJournal> updateUserOfJourna(@RequestBody UserOfJournal user,
                                                            @PathVariable String userName) {
        return ResponseEntity.ok().body(userService.update(user, userName));
    }








}
