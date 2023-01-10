package com.example.antologic.user;

import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserDTOExtended;
import com.example.antologic.user.dto.UserForm;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User createUser(UserForm userForm);

    List<UserDTOExtended> findUsers();

    Optional<UserDTOExtended> findUser(UUID uuid);

    UserDTO editUser(UUID uuid, UserForm userForm);

    void deleteUser(UUID uuid);
}
