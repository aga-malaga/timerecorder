package com.example.timerecorder.user.controller;

import com.example.timerecorder.common.dto.PageDTO;
import com.example.timerecorder.project.dto.SearchCriteria;
import com.example.timerecorder.user.UserFacade;
import com.example.timerecorder.user.dto.UserCreateForm;
import com.example.timerecorder.user.dto.UserDto;
import com.example.timerecorder.user.dto.UserUpdateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/users")
class UserController {
    private final UserFacade userService;

    @GetMapping
    public PageDTO getUsersPaged(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "surname") String sort) {
        return userService.findUsers(page, size, sort);
    }

    @GetMapping("/filter")
    public PageDTO filterUsers(@RequestParam UUID adminUuid,
                               @RequestBody(required = false) SearchCriteria searchCriteria,
                               Pageable page) {
        return userService.filterUsers(adminUuid, searchCriteria, page);
    }

    @PostMapping
    public UserDto createUser(@RequestBody @Valid UserCreateForm userCreateForm) {
        return userService.createUser(userCreateForm);
    }

    @PutMapping
    public void updateUser(@RequestBody @Valid UserUpdateForm userUpdateForm) {
        userService.editUser(userUpdateForm);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam UUID uuid) {
        userService.deleteUser(uuid);
    }
}