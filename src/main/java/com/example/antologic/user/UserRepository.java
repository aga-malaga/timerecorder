package com.example.antologic.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    User save(User user);
    void deleteByUuid(UUID uuid);
    Optional<User> findByUuid(UUID uuid);

    User findByLogin(String login);

    boolean existsByUuid(UUID uuid);

    boolean existsByLogin(String login);

    @Transactional
    Long removeUserByUuid(UUID uuid);
}
