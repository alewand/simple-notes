package me.alewand.server.controllers;

import org.springframework.web.bind.annotation.RestController;

import me.alewand.server.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class.getName());

    @GetMapping("/")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal User user) {
        logger.info("User {} requested their information", user.getUsername());
        return ResponseEntity.ok(user);
    }

}
