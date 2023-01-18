package com.example.antologic.controller;

import com.example.antologic.filter.SearchCriteria;
import com.example.antologic.service.UserService;
import com.example.antologic.user.User;
import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserForm;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/users")
class UserController {
    private final UserService userService;

    @GetMapping(params = {"!sort", "!page", "!size"})
    public List<UserDTO> getUsers(@RequestParam UUID adminUuid) {
        return userService.findUsers(adminUuid);
    }

    @GetMapping
    public Page<User> getUsersPaged(@RequestParam UUID adminUuid,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String sort)
    {
        return userService.findUsersPaged(adminUuid, page, size, sort);
    }

    @GetMapping("/filter")
    public Page<User> filterUsers(@RequestParam UUID adminUuid,
                                     @RequestBody(required = false) SearchCriteria searchCriteria,
                                     Pageable page) {
        return userService.filterUsers(adminUuid, searchCriteria, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestParam UUID adminUuid, @RequestBody @Valid UserForm userForm) {
        return userService.createUser(adminUuid, userForm);
    }

    @PutMapping
    public void updateUser(@RequestParam UUID adminUuid, @RequestParam UUID uuid, @RequestBody @Valid UserForm userForm) {
        userService.editUser(adminUuid, uuid, userForm);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam UUID adminUuid, @RequestParam UUID uuid) {
        userService.deleteUser(adminUuid, uuid);
    }
}
