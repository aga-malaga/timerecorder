package com.example.antologic.repository;

import com.example.antologic.project.Project;
import com.example.antologic.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    @EntityGraph(attributePaths = {"users"})
    boolean existsByUuid(UUID uuid);

    @EntityGraph(attributePaths = {"projects"})
    Page<Project> findAll(@Nullable Specification<Project> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"users"})
    boolean existsByName(String name);

    @EntityGraph(attributePaths = {"users"})
    Optional<Project> findProjectByUuid(UUID projectUuid);

    @EntityGraph(attributePaths = {"users"})
    Page<Project> findAll(Pageable page);

}
