package com.daniilPLatotonov.d.controllers;

import com.daniilPLatotonov.d.Service.UserService;
import com.daniilPLatotonov.d.model.response.UserResponse;
import com.daniilPLatotonov.d.model.request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @GetMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    public UserResponse findById(@PathVariable Integer userId) {
        return userService.findById(userId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public UserResponse create(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PatchMapping(value = "/{userId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public UserResponse update(@PathVariable Integer userId, @RequestBody CreateUserRequest request) {
        return userService.update(userId, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Integer userId) {
        userService.delete(userId);
    }


}
