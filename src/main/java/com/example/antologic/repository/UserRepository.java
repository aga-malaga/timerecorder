package com.example.antologic.repository;

import com.example.antologic.user.Role;
import com.example.antologic.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    List<User> findAll(Specification<User> specification);

    Page<User> findAll(@Nullable Specification<User> spec, Pageable pageable);

    User save(User user);

    User findUserByRole(Role role);

    Optional<User> findUserByUuidAndRole(UUID uuid, Role role);

    Optional<User> findByUuid(UUID uuid);

    boolean existsByUuid(UUID uuid);

    boolean existsByLogin(String login);

    void removeUserByUuid(UUID uuid);
}
