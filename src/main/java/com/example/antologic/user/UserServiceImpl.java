package com.example.antologic.user;

import com.example.antologic.common.UserNotFoundException;
import com.example.antologic.customSecurity.Validator;
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
    private final Validator validator;

    @Override
    public User createUser(UserForm userForm) {
        validator.validateEnum(userForm.getRole().getRole());
        User user = Mapper.mapFormToUser(userForm);
        return userRepository.save(user);
    }

    @Override
    public List<UserDTOExtended> findUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(Mapper::mapUserToDtoExtended)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTOExtended> findUser(final UUID uuid) {
        return Optional.ofNullable(userRepository
                .findByUuid(uuid).map(Mapper::mapUserToDtoExtended)
                .orElseThrow(() -> new UserNotFoundException("User with id " + uuid + " not found")));
    }

    @Override
    public UserDTO editUser(final UUID uuid, final UserForm userForm) {
        if (!userRepository.existsByLogin(userForm.getLogin())) {
            throw new UserNotFoundException("User with id " + uuid + " not found");
        }
        final User user = userRepository.findByLogin(userForm.getLogin());
        user.setName(userForm.getName());
        user.setSurname(userForm.getSurname());
        user.setRole(userForm.getRole());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setCostPerHour(userForm.getCostPerHour());
        userRepository.save(user);
        return Mapper.mapUserToDto(user);

    }

    public void deleteUser(UUID uuid) {
        userRepository.removeUserByUuid(uuid);
    }

}
