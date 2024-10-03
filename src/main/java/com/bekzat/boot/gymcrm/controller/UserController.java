package com.bekzat.boot.gymcrm.controller;

import com.bekzat.boot.gymcrm.dto.CredentialsDto;
import com.bekzat.boot.gymcrm.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        if (userService.login(new CredentialsDto(username, password))) {
            return ResponseEntity.ok("200 OK");
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String username, @RequestParam String password, @RequestParam String newPassword) {
        if (userService.changePassword(new CredentialsDto(username, password), newPassword)) {
            return ResponseEntity.ok("200 OK");
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }
}
