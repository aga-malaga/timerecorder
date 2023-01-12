package com.example.antologic.service;

import com.example.antologic.common.NotFoundException;
import com.example.antologic.common.RecordAlreadyExistsException;
import com.example.antologic.customSecurity.AdminValidator;
import com.example.antologic.filter.SearchCriteria;
import com.example.antologic.user.User;
import com.example.antologic.user.UserMapper;
import com.example.antologic.user.UserRepository;
import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserForm;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Override
    public UserDTO createUser(UUID adminUuid, UserForm userForm) {
        validate(adminUuid);

        if (userRepository.existsByLogin(userForm.getLogin())) {
            throw new RecordAlreadyExistsException("This login already exists");
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
    public List<UserDTO> filter(UUID adminUuid, SearchCriteria searchCriteria) {
        final Specification<User> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchCriteria.name() != null) {
                predicates.add(builder.equal(root.get("name"), searchCriteria.name()));
            }
            if (searchCriteria.surname() != null) {
                predicates.add(builder.equal(root.get("surname"), searchCriteria.surname()));
            }
            if (searchCriteria.costFrom() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("costPerHour"), searchCriteria.costFrom()));
            }
            if (searchCriteria.costTo() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("costPerHour"), searchCriteria.costTo()));
            }
            if (searchCriteria.role() != null) {
                predicates.add(builder.equal(root.get("role"), searchCriteria.role()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };

        return userRepository.findAll(specification).stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
}
