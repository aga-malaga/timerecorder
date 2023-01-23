package com.example.antologic.repository;

import com.example.antologic.user.Role;
import com.example.antologic.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @EntityGraph(attributePaths = {"projects"})
    List<User> findAll();
    @EntityGraph(attributePaths = {"projects.project"})
    Page<User> findAll(Pageable pageable);
    @EntityGraph(attributePaths = {"projects"})
    List<User> findAll(Specification<User> specification);
    @EntityGraph(attributePaths = {"projects"})
    Page<User> findAll(@Nullable Specification<User> spec, Pageable pageable);
    @EntityGraph(attributePaths = {"projects"})
    User save(User user);
    @EntityGraph(attributePaths = {"projects"})
    User findUserByRole(Role role);
    @EntityGraph(attributePaths = {"projects"})
    Optional<User> findUserByUuidAndRole(UUID uuid, Role role);
    @EntityGraph(attributePaths = {"projects"})
    Optional<User> findByUuid(UUID uuid);
    @EntityGraph(attributePaths = {"projects"})
    boolean existsByUuid(UUID uuid);
    @EntityGraph(attributePaths = {"projects"})
    boolean existsByLogin(String login);
    @EntityGraph(attributePaths = {"projects"})
    void removeUserByUuid(UUID uuid);
}
