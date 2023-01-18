package com.example.antologic.repository;

import com.example.antologic.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByUuid(UUID uuid);

    boolean existsByName(String name);

}
