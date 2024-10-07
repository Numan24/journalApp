package com.numan.journalapp.controller;

import com.numan.journalapp.entity.UserOfJournal;
import com.numan.journalapp.service.JournalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("register")
@RequiredArgsConstructor
public class RegisterUserController {

    private final JournalUserService userService;

    @GetMapping
    public ResponseEntity<String> healthChecker() {
        return ResponseEntity.ok().body("ok");
    }

    @PostMapping
    public ResponseEntity<UserOfJournal> registerUser(@RequestBody UserOfJournal user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(user));
    }
}
