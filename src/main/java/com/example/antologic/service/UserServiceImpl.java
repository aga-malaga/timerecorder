package com.example.antologic.service;

import com.example.antologic.common.UserAlreadyExistsException;
import com.example.antologic.common.UserNotFoundException;
import com.example.antologic.customSecurity.AdminValidator;
import com.example.antologic.customSecurity.EnumValidator;
import com.example.antologic.user.Mapper;
import com.example.antologic.user.User;
import com.example.antologic.user.UserRepository;
import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserDTOExtended;
import com.example.antologic.user.dto.UserForm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EnumValidator enumValidator;

    private final AdminValidator validateAdmin;

    public boolean validate(UUID admin) {
        return validateAdmin.validateAdmin(admin);
    }

    @Override
    public User createUser(UUID adminUuid, UserForm userForm) {
        validate(adminUuid);

        if (userRepository.existsByLogin(userForm.getLogin())) {
            throw new UserAlreadyExistsException("This login already exists");
        }
        enumValidator.validateEnum(userForm.getRole().getRole());
        User user = Mapper.mapFormToUser(userForm);
        return userRepository.save(user);
    }

    @Override
    public List<UserDTOExtended> findUsers(UUID adminUuid) {
        validate(adminUuid);

        return userRepository
                .findAll()
                .stream()
                .map(Mapper::mapUserToDtoExtended)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findUsersDto(final UUID adminUuid) {
        validate(adminUuid);

        return userRepository
                .findAll()
                .stream()
                .map(Mapper::mapUserToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTOExtended> findUser(final UUID adminUuid, final UUID uuid) {
        validate(adminUuid);

        return Optional.ofNullable(userRepository
                .findByUuid(uuid).map(Mapper::mapUserToDtoExtended)
                .orElseThrow(() -> new UserNotFoundException("User with id " + uuid + " not found")));
    }

    @Override
    public UserDTO editUser(final UUID adminUuid, final UUID uuid, final UserForm userForm) {
        validate(adminUuid);

        final User user = userRepository.findByUuid(uuid).orElseThrow(() ->
                new UserNotFoundException("User with id " + uuid + " not found"));
        user.setLogin(user.getLogin());
        user.setName(userForm.getName());
        user.setSurname(userForm.getSurname());
        user.setRole(userForm.getRole());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setCostPerHour(userForm.getCostPerHour());
        userRepository.save(user);

        return Mapper.mapUserToDto(user);

    }

    public void deleteUser(UUID adminUuid, UUID uuid) {
        validate(adminUuid);

        if (!userRepository.existsByUuid(uuid)) {
            throw new UserNotFoundException("User with id " + uuid + " not found");
        }
        userRepository.removeUserByUuid(uuid);
    }

}
