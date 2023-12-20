package com.example.timerecorder.user.domain;

import com.example.timerecorder.common.dto.PageDTO;
import com.example.timerecorder.common.dto.PageMapper;
import com.example.timerecorder.common.exception.AlreadyExistsException;
import com.example.timerecorder.common.exception.NoContentException;
import com.example.timerecorder.common.exception.NotFoundException;
import com.example.timerecorder.customSecurity.AdminValidator;
import com.example.timerecorder.project.dto.SearchCriteria;
import com.example.timerecorder.user.UserFacade;
import com.example.timerecorder.user.dto.UserCreateForm;
import com.example.timerecorder.user.dto.UserDto;
import com.example.timerecorder.user.dto.UserMapper;
import com.example.timerecorder.user.dto.UserUpdateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserFacade {

    private final UserRepository userRepository;
    private final AdminValidator validateAdmin;

    public PageDTO findUsers(UUID adminUuid, int pageNo, int pageSize, String sortBy) {
        validate(adminUuid);

        Pageable p = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        final Page<UserDto> pageUserDTO = userRepository.findAll(p).map(UserMapper::toDto);
        return PageMapper.toDtoUser(pageUserDTO);
    }

    public void validate(UUID admin) {
        validateAdmin.validate(admin);
    }

    @Override
    public UserDto createUser(UUID adminUuid, UserCreateForm userCreateForm) {
        validate(adminUuid);

        if (userRepository.existsByLogin(userCreateForm.getLogin())) {
            throw new AlreadyExistsException("This login already exists");
        }
        User user = UserMapper.toUser(userCreateForm);
        userRepository.save(user);
        return UserMapper.toDto(user);
    }

    @Override
    public void editUser(final UUID adminUuid, final UserUpdateForm userUpdateForm) {
        validate(adminUuid);

        final UUID userUuid = userUpdateForm.getUserUuid();
        final User user = userRepository.findByUuid(userUuid).orElseThrow(() ->
                new NotFoundException("User with id " + userUuid + " not found"));

        user.setLogin(userUpdateForm.getLogin());
        user.setName(userUpdateForm.getName());
        user.setSurname(userUpdateForm.getSurname());
        user.setRole(userUpdateForm.getRole());
        user.setEmail(userUpdateForm.getEmail());
        user.setPassword(userUpdateForm.getPassword());
        user.setCostPerHour(userUpdateForm.getCostPerHour());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(UUID adminUuid, UUID uuid) {
        validate(adminUuid);

        if (!userRepository.existsByUuid(uuid)) {
            throw new NotFoundException("User with id " + uuid + " not found");
        }
        userRepository.removeUserByUuid(uuid);
    }

    @Override
    public PageDTO filterUsers(UUID adminUuid, SearchCriteria searchCriteria, Pageable page) {
        if (searchCriteria == null) {
            throw new NoContentException("No criteria included");
        }
        Specification<User> specification = new UserSpecification(searchCriteria);

        final Page<UserDto> pageUserDto = userRepository.findAll(specification, page).map(UserMapper::toDto);
        return PageMapper.toDtoUser(pageUserDto);
    }
}

