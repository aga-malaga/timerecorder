package com.example.antologic.service;

import com.example.antologic.common.AlreadyExistsException;
import com.example.antologic.common.NoContentException;
import com.example.antologic.common.NotFoundException;
import com.example.antologic.customSecurity.AdminValidator;
import com.example.antologic.filter.SearchCriteria;
import com.example.antologic.repository.UserRepository;
import com.example.antologic.user.User;
import com.example.antologic.user.UserSpecification;
import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserForm;
import com.example.antologic.user.dto.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AdminValidator validateAdmin;

    @Override
    public List<UserDTO> findUsers(UUID adminUuid) {
        validate(adminUuid);

        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public void validate(UUID admin) {
        validateAdmin.validate(admin);
    }

    public Page<User> findUsersPaged(UUID adminUuid, Pageable p) {
        validate(adminUuid);
        return userRepository.findAll(p);
    }

    @Override
    public UserDTO createUser(UUID adminUuid, UserForm userForm) {
        validate(adminUuid);

        if (userRepository.existsByLogin(userForm.getLogin())) {
            throw new AlreadyExistsException("This login already exists");
        }
        User user = UserMapper.toUser(userForm);
        userRepository.save(user);
        return UserMapper.toDto(user);
    }

    @Override
    public void editUser(final UUID adminUuid, final UUID uuid, final UserForm userForm) {
        validate(adminUuid);

        final User user = userRepository.findByUuid(uuid).orElseThrow(() ->
                new NotFoundException("User with id " + uuid + " not found"));

        user.setLogin(user.getLogin());
        user.setName(userForm.getName());
        user.setSurname(userForm.getSurname());
        user.setRole(userForm.getRole());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setCostPerHour(userForm.getCostPerHour());
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
    public Page<User> filterUsers(UUID adminUuid, SearchCriteria searchCriteria, Pageable page) {
        if (searchCriteria == null) {
            throw new NoContentException("No criteria included");
        }
        Specification<User> specification = new UserSpecification(searchCriteria);

        return userRepository.findAll(specification, page);
    }
}
