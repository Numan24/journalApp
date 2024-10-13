package com.numan.journalapp.controller;

import com.numan.journalapp.entity.UserOfJournal;
import com.numan.journalapp.service.JournalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

  private final JournalUserService userService;

  @GetMapping("/{pageNum}")
  public ResponseEntity<Page<UserOfJournal>> getAllUsers(@PathVariable int pageNum) {

    Page<UserOfJournal> allUsers = userService.getAllUsers(PageRequest.of(pageNum,
      10,
      Sort.Direction.DESC, "id"));

    if (allUsers != null && !allUsers.isEmpty()) {
      return ResponseEntity.ok().body(allUsers);
    }

    return ResponseEntity.notFound().build();
  }
}
