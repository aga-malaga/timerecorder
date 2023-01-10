package com.example.antologic.controller;

import com.example.antologic.user.User;
import com.example.antologic.user.UserService;
import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserDTOExtended;
import com.example.antologic.user.dto.UserForm;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/user")
class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDTOExtended> getUsers() {
        return userService.findUsers();
    }

    @GetMapping("/{uuid}")
    public UserDTOExtended getUser(@PathVariable UUID uuid) {
        return userService.findUser(uuid).get();
    }

    @PostMapping
    public User create(@RequestBody @Valid UserForm userForm) {
        log.info("creating user");
        return userService.createUser(userForm);
    }

    @PutMapping("/{uuid}")
    public UserDTO updateUser(@PathVariable("uuid") UUID uuid, @RequestBody @Valid UserForm userForm) {
        log.info("updating user");
        return userService.editUser(uuid, userForm);
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable("uuid") UUID uuid) {
        userService.deleteUser(uuid);
    }


}
