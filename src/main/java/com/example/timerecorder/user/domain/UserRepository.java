package com.example.timerecorder.user.domain;

import com.example.timerecorder.common.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @EntityGraph(attributePaths = {"projects.project"})
    Page<User> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"projects"})
    Page<User> findAll(@Nullable Specification<User> spec, Pageable pageable);

    User save(User user);

    Optional<User> findUserByUuidAndRole(UUID uuid, Role role);

    Optional<User> findByUuid(UUID uuid);

    boolean existsByUuid(UUID uuid);

    boolean existsByLogin(String login);

    void removeUserByUuid(UUID uuid);
}
