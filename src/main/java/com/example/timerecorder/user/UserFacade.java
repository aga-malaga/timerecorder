package com.example.timerecorder.user;

import com.example.timerecorder.common.dto.PageDTO;
import com.example.timerecorder.user.dto.LoginRequest;
import com.example.timerecorder.project.dto.SearchCriteria;
import com.example.timerecorder.user.domain.User;
import com.example.timerecorder.user.dto.UserCreateForm;
import com.example.timerecorder.user.dto.UserDto;
import com.example.timerecorder.user.dto.UserSecurity;
import com.example.timerecorder.user.dto.UserUpdateForm;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface UserFacade {
    Optional<User> findByLogin(String login);
    PageDTO findUsers(int pageNo, int pageSize, String sortBy);
    UserDto createUser(UserCreateForm userCreateForm);
    void editUser(UserUpdateForm userUpdateForm);
    void deleteUser(UUID uuid);
    PageDTO filterUsers(UUID adminUuid, SearchCriteria searchCriteria, Pageable page);
    UserSecurity validate(LoginRequest loginRequest);
}