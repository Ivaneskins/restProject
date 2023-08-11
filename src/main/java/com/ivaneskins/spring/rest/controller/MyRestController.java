package com.ivaneskins.spring.rest.controller;

import com.ivaneskins.spring.rest.exception_handling.NoSuchUserException;
import com.ivaneskins.spring.rest.exception_handling.UserIncorrectData;
import com.ivaneskins.spring.rest.model.User;
import com.ivaneskins.spring.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> showAllUsers() {
        List<User>allUsers = userService.getAllUsers();
        return allUsers;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        User user = userService.getUser(id);
        if(user == null) {
            throw new NoSuchUserException("There is no User with id = " +
                    id + " in DB");
        }
        return userService.getUser(id);
    }
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(NoSuchUserException exception) {
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(Exception exception) {
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
