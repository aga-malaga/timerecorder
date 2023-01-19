package com.example.antologic.service;

import com.example.antologic.common.dto.PageDTO;
import com.example.antologic.filter.SearchCriteria;
import com.example.antologic.user.User;
import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserCreateForm;
import com.example.antologic.user.dto.UserUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    PageDTO findUsers(UUID adminUuid, int pageNo, int pageSize, String sortBy);

    UserDTO createUser(UUID adminUuid, UserCreateForm userCreateForm);

    void editUser(UUID adminUuid, UserUpdateForm userUpdateForm);

    void deleteUser(UUID adminUuid, UUID uuid);

    PageDTO filterUsers(UUID adminUuid, SearchCriteria searchCriteria, Pageable page);


}
