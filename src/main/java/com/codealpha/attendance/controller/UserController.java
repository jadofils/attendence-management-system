package com.codealpha.attendance.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codealpha.attendance.model.User;

import com.codealpha.attendance.service.userservice.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController{
    private UserService userService;
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user){
User saveUser=userService.saveUser(user);
return ResponseEntity.status(HttpStatus.CREATED)
.body(saveUser);
    }

    //get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users=userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
