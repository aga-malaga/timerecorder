package com.example.timerecorder.user.domain;

import com.example.timerecorder.common.dto.PageDTO;
import com.example.timerecorder.common.exception.AlreadyExistsException;
import com.example.timerecorder.common.exception.NoContentException;
import com.example.timerecorder.common.exception.NotFoundException;
import com.example.timerecorder.project.dto.SearchCriteria;
import com.example.timerecorder.user.UserFacade;
import com.example.timerecorder.user.dto.LoginRequest;
import com.example.timerecorder.user.dto.UserCreateForm;
import com.example.timerecorder.user.dto.UserDto;
import com.example.timerecorder.user.dto.UserSecurity;
import com.example.timerecorder.user.dto.UserUpdateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.example.timerecorder.common.dto.PageMapper.toDtoUser;
import static com.example.timerecorder.user.domain.UserMapper.toDto;
import static com.example.timerecorder.user.domain.UserMapper.toEntity;
import static com.example.timerecorder.user.domain.UserMapper.update;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserFacade {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    public PageDTO findUsers(int pageNo, int pageSize, String sortBy) {
        Pageable p = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        final Page<UserDto> pageUserDTO = userRepository.findAll(p).map(UserMapper::toDto);
        return toDtoUser(pageUserDTO);
    }

    @Transactional
    @Override
    public UserDto createUser(UserCreateForm createForm) {
        verifyIfLoginExists(createForm);

        User user = toEntity(createForm, passwordEncoder.encode(createForm.getPassword()));
        userRepository.save(user);

        return toDto(user);
    }

    @Transactional
    @Override
    public void editUser(final UserUpdateForm updateForm) {
        final UUID userUuid = updateForm.getUserUuid();
        final User user = findUser(userUuid);

        update(user, updateForm, passwordEncoder.encode(updateForm.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(UUID uuid) {
        verifyIfUserExists(uuid);
        userRepository.removeUserByUuid(uuid);
    }

    @Transactional
    @Override
    public PageDTO filterUsers(UUID adminUuid, SearchCriteria searchCriteria, Pageable page) {
        if (searchCriteria == null) {
            throw new NoContentException("No criteria included");
        }
        Specification<User> specification = new UserSpecification(searchCriteria);

        final Page<UserDto> pageUserDto = userRepository.findAll(specification, page).map(UserMapper::toDto);

        return toDtoUser(pageUserDto);
    }

    @Transactional
    @Override
    public UserSecurity validate(final LoginRequest loginRequest) {
        return userRepository
                .findUserByLogin(loginRequest.login())
                .filter(user -> passwordEncoder.matches(loginRequest.password(), user.getPassword()))
                .map(UserMapper::toUserSecurity)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private void verifyIfUserExists(final UUID uuid) {
        if (!userRepository.existsByUuid(uuid)) {
            throw new NotFoundException("User with id " + uuid + " not found");
        }
    }

    private User findUser(final UUID userUuid) {
        return userRepository.findByUuid(userUuid).orElseThrow(() ->
                new NotFoundException("User with id " + userUuid + " not found"));
    }

    private void verifyIfLoginExists(final UserCreateForm userCreateForm) {
        if (userRepository.existsByLogin(userCreateForm.getLogin())) {
            throw new AlreadyExistsException("This login already exists");
        }
    }
}