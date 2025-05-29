package me.alewand.server.controllers;

import org.springframework.web.bind.annotation.RestController;

import me.alewand.server.models.User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }

}
