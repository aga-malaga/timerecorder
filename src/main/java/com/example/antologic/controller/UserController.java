package com.example.antologic.controller;

import com.example.antologic.filter.FilteringService;
import com.example.antologic.filter.SearchCriteria;
import com.example.antologic.service.UserService;
import com.example.antologic.user.User;
import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserDTOExtended;
import com.example.antologic.user.dto.UserForm;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/user")
class UserController {
    private final UserService userService;

    private final FilteringService filteringService;

    @GetMapping({"{adminUuid}"})
    public List<UserDTOExtended> getUsers(@PathVariable("adminUuid") UUID adminUuid) {
        return userService.findUsers(adminUuid);
    }

    @GetMapping({"{adminUuid}"})
    public List<User> getUsersFiltered(@PathVariable("adminUuid") UUID adminUuid,
                                       @RequestBody @Valid List<SearchCriteria> searchCriteria) {
        userService.validate(adminUuid);
        return filteringService.filter(searchCriteria);
    }

    @GetMapping("/dto/{adminUuid}")
    public List<UserDTO> getUsersDto(@PathVariable("adminUuid") UUID adminUuid) {
        return userService.findUsersDto(adminUuid);
    }

    @GetMapping("/{adminUuid}/{uuid}")
    public UserDTOExtended getUser(@PathVariable("adminUuid") UUID adminUuid, @PathVariable("uuid") UUID uuid) {
        return userService.findUser(adminUuid, uuid).get();
    }

    @PostMapping("{adminUuid}")
    public User create(@PathVariable("adminUuid") UUID adminUuid, @RequestBody @Valid UserForm userForm) {
        log.info("creating user");
        return userService.createUser(adminUuid, userForm);
    }

    @PutMapping("{adminUuid}/{uuid}")
    public UserDTO updateUser(@PathVariable("adminUuid") UUID adminUuid, @PathVariable("uuid") UUID uuid, @RequestBody @Valid UserForm userForm) {
        log.info("updating user");
        return userService.editUser(adminUuid, uuid, userForm);
    }

    @DeleteMapping("{uuid}")
    public void delete(@PathVariable("adminUuid") UUID adminUuid, @PathVariable("uuid") UUID uuid) {
        userService.deleteUser(adminUuid, uuid);
    }
}
