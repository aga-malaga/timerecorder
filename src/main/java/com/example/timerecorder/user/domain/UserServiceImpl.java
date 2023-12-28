package com.example.timerecorder.user.domain;

import com.example.timerecorder.common.dto.PageDTO;
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

import static com.example.timerecorder.common.dto.PageMapper.toDtoUser;
import static com.example.timerecorder.user.dto.UserMapper.toDto;
import static com.example.timerecorder.user.dto.UserMapper.toEntity;
import static com.example.timerecorder.user.dto.UserMapper.update;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserFacade {

    private final UserRepository userRepository;
    private final AdminValidator validateAdmin;

    public PageDTO findUsers(int pageNo, int pageSize, String sortBy) {
//        validate(adminUuid);

        Pageable p = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        final Page<UserDto> pageUserDTO = userRepository.findAll(p).map(UserMapper::toDto);
        return toDtoUser(pageUserDTO);
    }

    @Override
    public UserDto createUser(UserCreateForm createForm) {
//        validateAdmin(adminUuid);
        verifyIfLoginExists(createForm);

        User user = toEntity(createForm);
        userRepository.save(user);

        return toDto(user);
    }

    @Override
    public void editUser(final UserUpdateForm updateForm) {
//        validateAdmin(adminUuid);

        final UUID userUuid = updateForm.getUserUuid();
        final User user = findUser(userUuid);

        update(user, updateForm);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(UUID uuid) {
//        validateAdmin(adminUuid);
        verifyIfUserExists(uuid);
        userRepository.removeUserByUuid(uuid);
    }

    @Override
    public PageDTO filterUsers(UUID adminUuid, SearchCriteria searchCriteria, Pageable page) {
        if (searchCriteria == null) {
            throw new NoContentException("No criteria included");
        }
        Specification<User> specification = new UserSpecification(searchCriteria);

        final Page<UserDto> pageUserDto = userRepository.findAll(specification, page).map(UserMapper::toDto);

        return toDtoUser(pageUserDto);
    }

    private void validateAdmin(UUID admin) {
        validateAdmin.validate(admin);
    }

    private User findUser(final UUID userUuid) {
        return userRepository.findByUuid(userUuid).orElseThrow(() ->
                new NotFoundException("User with id " + userUuid + " not found"));
    }

    private void verifyIfUserExists(final UUID uuid) {
        if (!userRepository.existsByUuid(uuid)) {
            throw new NotFoundException("User with id " + uuid + " not found");
        }
    }

    private void verifyIfLoginExists(final UserCreateForm userCreateForm) {
        if (userRepository.existsByLogin(userCreateForm.getLogin())) {
            throw new AlreadyExistsException("This login already exists");
        }
    }
}