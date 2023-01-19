package com.example.antologic.repository;

import com.example.antologic.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @EntityGraph(attributePaths = {"users"})
    boolean existsByUuid(UUID uuid);

    @EntityGraph(attributePaths = {"users"})
    boolean existsByName(String name);

    @EntityGraph(attributePaths = {"users"})
    Optional<Project> findProjectByUuid(UUID projectUuid);

    @EntityGraph(attributePaths = {"users"})
    Page<Project> findAll(Pageable page);

}
