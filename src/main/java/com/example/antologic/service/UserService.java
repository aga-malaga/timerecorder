package com.example.antologic.service;

import com.example.antologic.filter.SearchCriteria;
import com.example.antologic.user.User;
import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserDTO> findUsers(UUID adminUuid);

    Page<User> findUsersPaged(UUID adminUuid, int pageNo, int pageSize, String sortBy);

    UserDTO createUser(UUID adminUuid, UserForm userForm);

    void editUser(UUID adminUuid, UUID uuid, UserForm userForm);

    void deleteUser(UUID adminUuid, UUID uuid);

    Page<User> filterUsers(UUID adminUuid, SearchCriteria searchCriteria, Pageable page);


}
