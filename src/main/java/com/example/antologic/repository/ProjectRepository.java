package com.example.antologic.repository;

import com.example.antologic.project.Project;
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
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    boolean existsByUuid(UUID uuid);

    @EntityGraph(attributePaths = {"users.user"})
    Page<Project> findAll(@Nullable Specification<Project> spec, Pageable pageable);

    boolean existsByName(String name);

    Optional<Project> findProjectByUuid(UUID projectUuid);

    @EntityGraph(attributePaths = {"users.user"})
    Page<Project> findAll(Pageable page);

    void removeProjectByUuid(UUID projectUuid);


}
