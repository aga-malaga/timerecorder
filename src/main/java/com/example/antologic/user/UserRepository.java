package com.example.antologic.user;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    List<User> findAll();

    List<User> findAll(Specification<User> specification);

    User save(User user);

    void deleteByUuid(UUID uuid);

    Optional<User> findByUuid(UUID uuid);

    boolean existsByUuid(UUID uuid);

    boolean existsByLogin(String login);

    @Transactional
    Long removeUserByUuid(UUID uuid);
}
