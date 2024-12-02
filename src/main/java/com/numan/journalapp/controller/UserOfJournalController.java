package com.numan.journalapp.controller;

import com.numan.journalapp.dto.WeatherResponseDTO;
import com.numan.journalapp.entity.UserOfJournal;
import com.numan.journalapp.service.JournalUserService;
import com.numan.journalapp.service.WeatherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserOfJournalController {

    private final JournalUserService userService;
    private final WeatherService weatherService;

    public UserOfJournalController(JournalUserService userService, WeatherService weatherService) {
        this.userService =  userService;
        this.weatherService = weatherService;
    }

    @GetMapping("/all-journals")
    public ResponseEntity<Page<UserOfJournal>> getAllJournalEntriesOfUser(@RequestParam Integer count) {
        Page<UserOfJournal> userOfJournal = userService.allJournalEntriesOfUser(PageRequest.of(count,
          10,
          Sort.Direction.DESC, "id"));

        return userOfJournal.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(userOfJournal);

    }

    @GetMapping
    public ResponseEntity<UserOfJournal> findJournalUser() {
        return ResponseEntity.ok().body(userService.findingLoggedInUser());

    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        return ResponseEntity.ok().body(userService.deleteUserByName() ? "successfully deleted" : "not found");
    }

    @PutMapping
    public ResponseEntity<UserOfJournal> updateUserOfJournal(@RequestBody UserOfJournal user) {
        return ResponseEntity.ok().body(userService.update(user));
    }

    @GetMapping("/hi")
    public ResponseEntity<String> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponseDTO weatherResponseDTO = weatherService.getWeather("Karachi");
        return ResponseEntity.ok().body("Hi " + authentication.getName() + " Karachi weather feels like " + weatherResponseDTO.getCurrent().getFeelsLike());
    }



}
