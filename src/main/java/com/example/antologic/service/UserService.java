package com.example.antologic.service;

import com.example.antologic.filter.SearchCriteria;
import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserForm;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserDTO> findUsers(UUID adminUuid);

    UserDTO createUser(UUID adminUuid, UserForm userForm);

    void editUser(UUID adminUuid, UUID uuid, UserForm userForm);

    void deleteUser(UUID adminUuid, UUID uuid);

    List<UserDTO> filter(UUID adminUuid, SearchCriteria searchCriteria);

}
