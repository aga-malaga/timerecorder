package com.example.antologic.service;

import com.example.antologic.user.User;
import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserDTOExtended;
import com.example.antologic.user.dto.UserForm;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    boolean validate(UUID admin);

    User createUser(UUID adminUuid, UserForm userForm);

    List<UserDTOExtended> findUsers(UUID adminUuid);

    List<UserDTO> findUsersDto(UUID adminUuid);

    Optional<UserDTOExtended> findUser(UUID adminUuid, UUID uuid);

    UserDTO editUser(UUID adminUuid, UUID uuid, UserForm userForm);

    void deleteUser(UUID adminUuid, UUID uuid);
}
