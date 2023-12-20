package com.example.timerecorder.projectUser.domain;

import com.example.timerecorder.project.Project;
import com.example.timerecorder.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, ProjectUserId> {

    @EntityGraph(attributePaths = {"project", "user"})
    Optional<ProjectUser> findByProjectAndUser(Project project, User user);

    @EntityGraph(attributePaths = {"projects", "users"})
    Page<ProjectUser> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"project", "user"})
    Optional<ProjectUser> findProjectUserByUser(User user);

    @EntityGraph(attributePaths = {"project", "user"})
    List<ProjectUser> findProjectUsersByUser(User user);



}
