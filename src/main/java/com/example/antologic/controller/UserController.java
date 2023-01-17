package com.example.antologic.controller;

import com.example.antologic.filter.SearchCriteria;
import com.example.antologic.service.UserService;
import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserForm;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/users")
class UserController {
    private final UserService userService;

    @GetMapping({"{adminUuid}"})
    public List<UserDTO> getUsers(@PathVariable("adminUuid") UUID adminUuid) {
        return userService.findUsers(adminUuid);
    }

    @PostMapping("/showusers/{adminUuid}")
    public List<UserDTO> filterUsers(@PathVariable("adminUuid") UUID adminUuid,
                                     @RequestBody SearchCriteria searchCriteria) {
        return userService.filter(adminUuid, searchCriteria);
    }

    @PostMapping("{adminUuid}")
    public UserDTO create(@PathVariable("adminUuid") UUID adminUuid, @RequestBody @Valid UserForm userForm) {
        return userService.createUser(adminUuid, userForm);
    }

    @PutMapping("{adminUuid}/{uuid}")
    public void updateUser(@PathVariable("adminUuid") UUID adminUuid, @PathVariable("uuid") UUID uuid, @RequestBody @Valid UserForm userForm) {
        userService.editUser(adminUuid, uuid, userForm);
    }

    @DeleteMapping("{adminUuid}/{uuid}")
    public void delete(@PathVariable("adminUuid") UUID adminUuid, @PathVariable("uuid") UUID uuid) {
        userService.deleteUser(adminUuid, uuid);
    }
}
