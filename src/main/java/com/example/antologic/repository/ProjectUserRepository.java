package com.example.antologic.repository;

import com.example.antologic.project.Project;
import com.example.antologic.projectUser.ProjectUser;
import com.example.antologic.projectUser.ProjectUserId;
import com.example.antologic.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser,
        ProjectUserId> {

    @EntityGraph(attributePaths = {"project", "user"})
    Optional<ProjectUser> findByProjectAndUser(Project project, User user);

    @EntityGraph(attributePaths = {"project", "user"})
    Page<ProjectUser> findAll(@Nullable Specification<Project> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"project", "user"})
    Page<ProjectUser> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"project","user"})
    Optional<ProjectUser> findProjectUserByUser(User user);



}
